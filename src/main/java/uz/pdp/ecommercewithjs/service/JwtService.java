package uz.pdp.ecommercewithjs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.pdp.ecommercewithjs.entity.Role;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.repo.RoleRepository;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {


    private final RoleRepository roleRepository;

    public JwtService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String generateToken(User user) {
        Map<String , Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Role role : user.getRoles()) {
            stringJoiner.add(role.getRoleName().toString());
        }
        claims.put("roles", stringJoiner.toString());
        claims.put("fullname", user.getFullname());

        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .signWith(getSecretKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .compact();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor("01234567890123456789012345678912".getBytes());
    }
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
//    public String getUserName(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(getSecretKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//        return claims.getSubject();
//    }
public String getUserName(String token) {
    Claims claims = getClaims(token);

    return claims.get("username", String.class);
}


    public User getUserFromToken(String token) {
        try {
            Claims claims = getClaims(token);

            String username = claims.getSubject();
            Integer userId = (Integer) claims.get("id");
            String fullname = (String) claims.get("fullname");

            User user = User.builder()
                    .username(username)
                    .fullname(fullname)
                    .id(userId)
                    .build();

            System.out.println("Parsed User: " + user);
            System.out.println("Parsing token: " + token);
            System.out.println("Claims = " + claims);
            System.out.println("Parsed User: " + user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SimpleGrantedAuthority> getRoles(String token) {
        Claims claims = getClaims(token);

        String rolesString = claims.get("roles", String.class);
        if (rolesString == null || rolesString.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(rolesString.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
