package com.eventmanagement.EventManagementBackend.usecase.auth.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.auth.TokenServiceUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenServiceUseCaseImpl implements TokenServiceUseCase {
    private final JwtEncoder jwtEncoder;
    private final UsersAccountRepository usersRepository;
    private final JwtDecoder jwtDecoder;

    private final long ACCESS_TOKEN_EXPIRY = 2600L; // 45 minutes
    private final long REFRESH_TOKEN_EXPIRY = 86400L; // 24 hours

    public TokenServiceUseCaseImpl(JwtEncoder jwtEncoder, UsersAccountRepository usersRepository, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.usersRepository = usersRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public String generateToken(Authentication authentication, TokenType tokenType) {
        Instant now = Instant.now();
        long expiry = (tokenType == TokenType.ACCESS) ? ACCESS_TOKEN_EXPIRY : REFRESH_TOKEN_EXPIRY;

        String email = authentication.getName();

        UsersAccount user = usersRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + " " + b)
                .orElse("");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(email)
                .claim("scope", scope)
                .claim("userId", user.getUserId())
                .claim("name", user.getName())
                .claim("type", tokenType.name())
                .build();

        //using secret key
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        //using rsa key
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        Jwt jwt = this.jwtDecoder.decode(refreshToken);
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRY))
                .subject(jwt.getSubject())
                .claim("scope", jwt.getClaimAsString("scope"))
                .claim("userId", jwt.getClaimAsString("userId"))
                .claim("type", TokenType.ACCESS.name())
                .claim("name", jwt.getClaims().get("name"))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}

