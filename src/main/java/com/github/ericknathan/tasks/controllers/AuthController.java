package com.github.ericknathan.tasks.controllers;

import com.github.ericknathan.tasks.dtos.user.UserAuthDetailsDTO;
import com.github.ericknathan.tasks.dtos.user.UserLoginDTO;
import com.github.ericknathan.tasks.dtos.user.UserRegisterDTO;
import com.github.ericknathan.tasks.models.UserModel;
import com.github.ericknathan.tasks.repositories.UserRepository;
import com.github.ericknathan.tasks.services.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegisterDTO dto, UriComponentsBuilder builder){
        var userAlreadyExists = userRepository.findByEmail(dto.email()) != null;
        if(userAlreadyExists) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        var user = new UserModel(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        var uri = builder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("login")
    public ResponseEntity<UserAuthDetailsDTO> loginUser(@RequestBody @Valid UserLoginDTO user){
        try {
            var token = new UsernamePasswordAuthenticationToken(user.email(), user.password());
            var authentication = manager.authenticate(token);

            var tokenJwt = tokenService.generateToken((UserModel) authentication.getPrincipal());
            return ResponseEntity.ok(new UserAuthDetailsDTO(tokenJwt));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
