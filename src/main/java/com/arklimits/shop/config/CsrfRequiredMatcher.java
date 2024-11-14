package com.arklimits.shop.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfRequiredMatcher implements RequestMatcher {

    private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
        if (ALLOWED_METHODS.matcher(request.getMethod()).matches()) {
            return false;
        }

        final String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/swagger-ui")) {
            return false;
        }
        return true;
    }
}