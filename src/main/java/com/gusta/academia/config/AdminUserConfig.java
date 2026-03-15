package com.gusta.academia.config;

import com.gusta.academia.model.Role;
import com.gusta.academia.model.User;
import com.gusta.academia.repository.RoleRepository;
import com.gusta.academia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var usuarioAdmin = userRepository.findByUsername("admin");

        usuarioAdmin.ifPresentOrElse(
                user -> System.out.println("admin já existe"),

                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(encoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );

    }
}
