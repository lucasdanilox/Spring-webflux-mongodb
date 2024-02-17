package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;


    public Mono<PostDTO> findById(String id) {
        return repository.findById(id)
                .map(PostDTO::new)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("id not found")));
    }

    public Flux<PostDTO> findByTitle(String text) {
        return repository.searchTitle(text)
                .map(PostDTO::new);
    }

}
