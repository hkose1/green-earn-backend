package com.greenearn.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.greenearn.authservice.entity.RoleEntity;
import com.greenearn.authservice.enums.Role;
import com.greenearn.authservice.repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

   @Bean
   public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
       return args -> {
            if (roleRepository.findByRole(Role.USER).isEmpty()) {
            var userRole = new RoleEntity();
            userRole.setRole(Role.USER);

            roleRepository.save(userRole);
            }
            if (roleRepository.findByRole(Role.ADMIN).isEmpty()) {
            var adminRole = new RoleEntity();
            adminRole.setRole(Role.ADMIN);
            roleRepository.save(adminRole);
            }
       };
   }

}
