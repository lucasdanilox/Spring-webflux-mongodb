package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.entities.User;
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
        return repository.findAll().map(UserDTO::new);

    }

    public Mono<UserDTO> findById(String id) {
        return repository.findById(id)
                .map(UserDTO::new)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("id not found")));
    }

    public Mono<UserDTO> insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        return repository.save(entity).map(UserDTO::new);
    }

    public Mono<UserDTO> update(String id, UserDTO dto) {
        return repository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(dto.getName());
                    existingUser.setEmail(dto.getEmail());
                    return repository.save(existingUser);
                })
                .map(UserDTO::new)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("user not found")));
    }


    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("user not found")))
                .flatMap(existingUser -> repository.delete(existingUser));
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

    }
}
