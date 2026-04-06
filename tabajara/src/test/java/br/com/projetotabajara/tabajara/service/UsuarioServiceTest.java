package br.com.projetotabajara.tabajara.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveNormalizarDadosECriptografarSenhaAoSalvar() {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("  Maria da Silva  ");
        usuario.setEmailUsuario("  MARIA@TABAJARA.COM  ");
        usuario.setLoginUsuario("  maria  ");
        usuario.setSenhaUsuario(" 123456 ");

        when(usuarioRepository.findByLoginUsuarioIgnoreCase("maria")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmailUsuarioIgnoreCase("maria@tabajara.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("senha-criptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario salvo = usuarioService.save(usuario);

        assertEquals("Maria da Silva", salvo.getNomeUsuario());
        assertEquals("maria@tabajara.com", salvo.getEmailUsuario());
        assertEquals("maria", salvo.getLoginUsuario());
        assertEquals("senha-criptografada", salvo.getSenhaUsuario());
        assertEquals("ROLE_USER", salvo.getRole());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveImpedirCadastroComEmailDuplicado() {
        Usuario usuario = new Usuario();
        usuario.setEmailUsuario("maria@tabajara.com");
        usuario.setLoginUsuario("maria");
        usuario.setSenhaUsuario("123456");

        Usuario existente = new Usuario();
        existente.setIdUsuario(99);

        when(usuarioRepository.findByLoginUsuarioIgnoreCase("maria")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmailUsuarioIgnoreCase("maria@tabajara.com"))
                .thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> usuarioService.save(usuario));
    }
}
