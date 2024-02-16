package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Flux<UserDTO> findAll() {
        return repository.findAll().map(user -> new UserDTO(user));

    }

    public Mono<UserDTO> findById(String id) {
        return repository.findById(id)
                .map(existingUser -> new UserDTO(existingUser))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("id not found")));
    }
}
