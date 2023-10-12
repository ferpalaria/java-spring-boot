package com.example.todolist.filter;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");

        var auth_encoded = authorization.substring("Basic".length()).trim();
        System.out.println(auth_encoded); // Basic ZGuaWVsZWxlTIzNDU= // Coded em Base 64

        byte[] auth_decoded = Base64.getDecoder().decode(auth_encoded);
        String authString = new String(auth_decoded);
        System.out.println(authString); // fer:123

        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validar user
        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "Usuário sem autorização");
        } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

            if (passwordVerify.verified) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }

        }
    }
}