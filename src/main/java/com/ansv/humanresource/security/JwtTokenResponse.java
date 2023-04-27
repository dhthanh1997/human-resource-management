package com.ansv.humanresource.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.ansv.humanresource.constants.Constants.JWT_AUTH_TOKEN_VALIDITY;

@Data
public class JwtTokenResponse {
    @JsonProperty("token_id")
    private String tokenId;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn = JWT_AUTH_TOKEN_VALIDITY;
}
