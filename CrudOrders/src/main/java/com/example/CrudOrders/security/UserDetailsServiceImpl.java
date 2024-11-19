package com.example.CrudOrders.security;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /********** CARGA DE USUARIO POR NOMBRE DE USUARIO **********/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var usuario = getById(username); // Busca el usuario por nombre de usuario.

        if (usuario == null) {
            throw new UsernameNotFoundException(username); // Si el usuario no se encuentra, lanza una excepción.
        }

        return User // Construye y retorna un objeto UserDetails con los datos del usuario.
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    /********** BÚSQUEDA DE USUARIO POR ID **********/
    public static Usuario getById(String username) {

        var password = "$2a$10$Mh2ZW.qppqLbeVODs.UwYO5y3209npPhs8jEIJELhUQw/y7OoAwTO"; // Contraseña encriptada del usuario.

        Usuario LunaUser = new Usuario(
                "LunaUser",
                password,
                Set.of("USER") // Crea un usuario de ejemplo.
        );

        var usuarios = List.of(LunaUser); // Lista de usuarios.

        return usuarios  // Busca y retorna el usuario que coincide con el nombre de usuario.
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }

}

