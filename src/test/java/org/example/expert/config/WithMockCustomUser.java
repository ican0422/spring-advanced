package org.example.expert.config;

import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default  123L;

    String email() default "test@gmail.com";

    UserRole userRole() default UserRole.USER;
}
