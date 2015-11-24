package net.menu.security;

import net.menu.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by griv.
 */
public class SecurityUtils {
    private static final String UNAUTHORIZED = "unauthorized";

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserName() {
        Authentication authentication = getAuthentication();
        return authentication == null ? UNAUTHORIZED : authentication.getName();
    }

    public static User getUser() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : (User) authentication.getPrincipal();
    }

    private SecurityUtils() {
    }
}
