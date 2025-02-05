//package uz.pdp.ecommercewithjs.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import uz.pdp.ecommercewithjs.entity.User;
//import uz.pdp.ecommercewithjs.repo.UserRepository;
//import uz.pdp.ecommercewithjs.service.JwtService;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@Component
//public class MyFilter extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    public MyFilter(JwtService jwtService, UserRepository userRepository) {
//        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("token");
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            filterChain.doFilter(request, response);
//        }
//        if (token != null) {
//            if (jwtService.validate(token)){
//                String username = jwtService.getUserName(token);
//                User user = userRepository.findByUsername(username).orElseThrow();
//
//                var authorities = user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
//                        .collect(Collectors.toList());
//
//                var authToken = new UsernamePasswordAuthenticationToken(
//                        user.getUsername(),
//                        null,
//                        authorities
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//    }
//}

package uz.pdp.ecommercewithjs.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.repo.UserRepository;
import uz.pdp.ecommercewithjs.service.CustomUserDetailService;
import uz.pdp.ecommercewithjs.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;

    public MyFilter(JwtService jwtService, UserRepository userRepository, CustomUserDetailService customUserDetailService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtService.validate(token)) {
                String username = jwtService.getUserName(token);
                User user = userRepository.findByUsername(username).orElseThrow();

//                var authorities = user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
//                        .collect(Collectors.toList());
//
//                var authToken = new UsernamePasswordAuthenticationToken(
//                        user.getUsername(),
//                        null,
//                        authorities
//                );
                List<SimpleGrantedAuthority> roles =  jwtService.getRoles(token);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        roles
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
