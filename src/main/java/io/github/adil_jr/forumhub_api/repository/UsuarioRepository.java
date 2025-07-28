package io.github.adil_jr.forumhub_api.repository;

import io.github.adil_jr.forumhub_api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findUserByEmail(String email);
}