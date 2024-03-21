package com.microservices.userservice.security;

import java.lang.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target({ ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : #this")
public @interface CurrentUser {

}
