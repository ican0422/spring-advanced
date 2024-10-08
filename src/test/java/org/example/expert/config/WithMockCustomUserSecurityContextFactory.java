package org.example.expert.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken
                (annotation.id(), "password");

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}