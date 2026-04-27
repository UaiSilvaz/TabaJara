package br.com.projetotabajara.tabajara.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario save(Usuario usuario) {
        usuario.setNomeUsuario(normalizarEspacos(usuario.getNomeUsuario()));
        usuario.setEmailUsuario(normalizarTexto(usuario.getEmailUsuario()).toLowerCase(Locale.ROOT));
        usuario.setLoginUsuario(normalizarTexto(usuario.getLoginUsuario()).toLowerCase(Locale.ROOT));
        String senhaLimpa = normalizarTexto(usuario.getSenhaUsuario());

        validarDuplicidade(usuario);
        usuario.setSenhaUsuario(passwordEncoder.encode(senhaLimpa));
        if (usuario.getRole() == null || usuario.getRole().isBlank()) {
            usuario.setRole("ROLE_USER");
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public String gerarTokenRecuperacao(String email) {
        String emailNormalizado = normalizarTexto(email).toLowerCase(Locale.ROOT);
        Usuario usuario = usuarioRepository.findByEmailUsuarioIgnoreCase(emailNormalizado).orElse(null);
        if (usuario == null) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setTokenExpiration(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);
        return token;
    }

    public boolean redefinirSenha(String token, String novaSenha) {
        Usuario usuario = usuarioRepository.findByResetToken(token);
        if (usuario == null || usuario.getTokenExpiration() == null
                || usuario.getTokenExpiration().isBefore(LocalDateTime.now())) {
            return false;
        }

        usuario.setSenhaUsuario(passwordEncoder.encode(normalizarTexto(novaSenha)));
        usuario.setResetToken(null);
        usuario.setTokenExpiration(null);
        usuarioRepository.save(usuario);
        return true;
    }

    private void validarDuplicidade(Usuario usuario) {
        Usuario usuarioComMesmoLogin = usuarioRepository.findByLoginUsuarioIgnoreCase(usuario.getLoginUsuario())
                .orElse(null);
        if (usuarioComMesmoLogin != null
                && !Objects.equals(usuarioComMesmoLogin.getIdUsuario(), usuario.getIdUsuario())) {
            throw new IllegalArgumentException("Login ja cadastrado.");
        }

        Usuario usuarioComMesmoEmail = usuarioRepository.findByEmailUsuarioIgnoreCase(usuario.getEmailUsuario())
                .orElse(null);
        if (usuarioComMesmoEmail != null
                && !Objects.equals(usuarioComMesmoEmail.getIdUsuario(), usuario.getIdUsuario())) {
            throw new IllegalArgumentException("Email ja cadastrado.");
        }
    }

    private String normalizarTexto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private String normalizarEspacos(String valor) {
        return normalizarTexto(valor).replaceAll("\\s+", " ");
    }
}
