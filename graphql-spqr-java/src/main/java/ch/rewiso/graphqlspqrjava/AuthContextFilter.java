package ch.rewiso.graphqlspqrjava;

import ch.rewiso.graphqlspqrjava.model.User;
import ch.rewiso.graphqlspqrjava.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthContextFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<HttpServletRequest> httpRequest = Optional.of(request);


        User user = httpRequest
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .flatMap(userRepository::findById)
                .orElse(null);

        SecurityContextHolder.getContext().setAuthentication(user);

        filterChain.doFilter(request, response);
    }

}