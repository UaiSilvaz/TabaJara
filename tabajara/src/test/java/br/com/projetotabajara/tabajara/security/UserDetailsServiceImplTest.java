package br.com.projetotabajara.tabajara.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void deveCarregarUsuarioPeloEmail() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.findByLoginUsuarioIgnoreCaseOrEmailUsuarioIgnoreCase("maria@tabajara.com",
                "maria@tabajara.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = userDetailsService.loadUserByUsername("  maria@tabajara.com  ");

        assertEquals("maria", userDetails.getUsername());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findByLoginUsuarioIgnoreCaseOrEmailUsuarioIgnoreCase("inexistente", "inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("inexistente"));
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNomeUsuario("Maria");
        usuario.setEmailUsuario("maria@tabajara.com");
        usuario.setLoginUsuario("maria");
        usuario.setSenhaUsuario("$2a$10$hash");
        usuario.setRole("ROLE_USER");
        return usuario;
    }
}
