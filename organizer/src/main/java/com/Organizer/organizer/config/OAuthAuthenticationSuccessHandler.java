// package com.Organizer.organizer.config;

// import java.io.IOException;

// import org.hibernate.annotations.Comment;
// import org.hibernate.validator.internal.util.logging.LoggerFactory;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.DefaultRedirectStrategy;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//             Authentication authentication) throws IOException, ServletException {
//         // TODO Auto-generated method stub
//         new DefaultRedirectStrategy().sendRedirect(request, response,"/user/");
//         // throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
//     }
// }