package uz.pdp.ecommercewithjs.dto;

import lombok.Value;

@Value
public class AuthResponse {
    String token;
    String role;
}
