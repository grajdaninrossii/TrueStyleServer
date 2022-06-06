package com.truestyle.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

public enum ERole {

    ROLE_ADMIN,
    ROLE_MODERATOR,
    ROLE_USER

}