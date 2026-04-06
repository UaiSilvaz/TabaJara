package br.com.projetotabajara.tabajara.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUsuario;

    @Column(nullable = false, length = 100)
    private String nomeUsuario;

    @Column(nullable = false, length = 100, unique = true)
    private String emailUsuario;

    @Column(nullable = false)
    private String senhaUsuario;

    @Column(nullable = false, length = 200, unique = true)
    private String loginUsuario;

    private String role = "ROLE_USER";

    // NOVOS CAMPOS PARA RECUPERAÇÃO DE SENHA

    // TOKEN UNICO PARA REDEFINIR SENHA
    private String resetToken;

    // data de expiração do token
    private LocalDateTime tokenExpiration;
}
