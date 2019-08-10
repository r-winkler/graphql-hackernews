package ch.rewiso.graphqlspqrjava.service;

import ch.rewiso.graphqlspqrjava.model.SigninPayload;
import ch.rewiso.graphqlspqrjava.model.User;
import ch.rewiso.graphqlspqrjava.respository.UserRepository;
import graphql.GraphQLException;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GraphQLMutation
    public SigninPayload login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user.getPassword().equals(passwordEncoder.encode(password))) {
            return new SigninPayload(user.getId(), user); // token is user id (DON'T DO THIS)
        } else {
            throw new GraphQLException("Invalid credentials");
        }
    }

    @GraphQLMutation
    public SigninPayload signup(String email, String password, String name) {
        User newUser = new User(name, email, passwordEncoder.encode(password));
        User createdUser = userRepository.save(newUser);
        return new SigninPayload(createdUser.getId(), createdUser);
    }


}
