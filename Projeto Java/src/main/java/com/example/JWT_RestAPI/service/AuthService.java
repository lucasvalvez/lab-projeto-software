package com.example.JWT_RestAPI.service;

import com.example.JWT_RestAPI.model.Usuario;
import com.example.JWT_RestAPI.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por operações relacionadas à autenticação e geração de
 * tokens JWT.
 *
 * @author <a href="mailto:joaopauloaramuni@gmail.com">João Paulo Aramuni</a>
 * @version 1.0
 * @since 2024-01-20
 */
@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;
    /**
     * Gera um token JWT para o nome de usuário fornecido.
     *
     * @param username O nome de usuário para o qual um token JWT será gerado.
     * @return Uma string contendo o token JWT gerado.
     */
    public String generateToken(String username) {
        String token = JwtUtil.generateToken(username);
        return token;
    }

    /**
     * Extrai o nome de usuário de um token JWT.
     *
     * @param token O token JWT do qual o nome de usuário será extraído.
     * @return Uma string contendo o nome de usuário extraído do token JWT.
     */
    public Usuario extractUserData(String token) {
        String email = JwtUtil.extractUsername(token);
        return usuarioService.findByEmail(email);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
