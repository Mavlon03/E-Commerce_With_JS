package uz.pdp.ecommercewithjs.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.ecommercewithjs.dto.AuthResponse;
import uz.pdp.ecommercewithjs.dto.LoginDto;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.service.JwtService;

import java.util.TimeZone;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tashkent"));
    }

    @PostMapping
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        User user = (User) authenticate.getPrincipal();
        System.out.println("FullName: " + user.getFullname());
        String token = jwtService.generateToken(user);

        String role = user.getRoles().stream()
                .findFirst()
                .map(roleObj -> roleObj.getRoleName().name())
                .orElse("ROLE_USER");

        return ResponseEntity.ok(new AuthResponse(token, role));
    }

}
