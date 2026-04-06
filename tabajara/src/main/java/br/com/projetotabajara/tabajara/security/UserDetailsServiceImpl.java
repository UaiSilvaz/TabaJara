package br.com.projetotabajara.tabajara.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String loginNormalizado = login == null ? "" : login.trim();

        Usuario usuario = usuarioRepository
                .findByLoginUsuarioIgnoreCaseOrEmailUsuarioIgnoreCase(loginNormalizado, loginNormalizado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + loginNormalizado));

        return new UserDetailsImpl(usuario);
    }
}
