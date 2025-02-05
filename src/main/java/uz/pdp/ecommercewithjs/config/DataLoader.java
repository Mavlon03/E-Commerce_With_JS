package uz.pdp.ecommercewithjs.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.ecommercewithjs.entity.Role;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.repo.RoleRepository;
import uz.pdp.ecommercewithjs.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

//@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataLoader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> all = roleRepository.findAll();
        User user = User.builder()
                .fullname("Akmalov Mavlon")
                .username("1")
                .roles(new ArrayList<>(List.of(all.get(0))))
                .password(passwordEncoder.encode("1"))
                .build();

        userRepository.save(user);

        User user1 = User.builder()
                .fullname("Akmalov Mustafo")
                .username("2")
                .roles(new ArrayList<>(List.of(all.get(0))))
                .password(passwordEncoder.encode("2"))
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .fullname("Qalandarov Qalandar")
                .username("3")
                .roles(new ArrayList<>(List.of(all.get(0))))
                .password(passwordEncoder.encode("3"))
                .build();

        userRepository.save(user2);


    }
}
