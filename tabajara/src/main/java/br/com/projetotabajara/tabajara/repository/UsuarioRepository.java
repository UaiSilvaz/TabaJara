package br.com.projetotabajara.tabajara.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetotabajara.tabajara.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLoginUsuario(String loginUsuario);

    Optional<Usuario> findByLoginUsuarioIgnoreCase(String loginUsuario);

    Optional<Usuario> findByEmailUsuarioIgnoreCase(String emailUsuario);

    Optional<Usuario> findByLoginUsuarioIgnoreCaseOrEmailUsuarioIgnoreCase(String loginUsuario, String emailUsuario);

    // Buscar usuario pelo email para recuperação de senha
    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    // Buscar usuario pelo token de redefinição de senha
    Usuario findByResetToken(String resetToken);

}
