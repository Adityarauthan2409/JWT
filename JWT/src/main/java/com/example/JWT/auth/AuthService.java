package com.example.JWT.auth;


import com.example.JWT.repository.UserRepository;
import com.example.JWT.service.JwtService;
import com.example.JWT.user.UserD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

            

    public Object register(UserD request) {
        request.setFirstname(request.getFirstname());
        request.setLastname(request.getLastname());
        request.setEmail(request.getEmail());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return repository.save(request);

    }




    public String authenticate(UserD request) throws Exception {
        
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        }catch(Exception e){
            throw new Exception("invalid username or password");
        }

        String token = jwtService.generateToken(request.getEmail());
        UserD userD = repository.findByEmail(request.getEmail()).get();
        userD.setAccess_token(token);
        repository.save(userD);
        return token;

        }

}
