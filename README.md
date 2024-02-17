# Spring Webflux com MongoDB

### Modelo conceitual

![Modelo de domínio ](https://i.postimg.cc/JzpfQGqJ/model-spring-mongodb.png)

### Container Docker do MongoDB para desenvolvimento

```
docker run -d -p 27017:27017 -v /data/db --name mongo1 mongo:4.4.3-bionic
```

```
docker exec -it mongo1 bash
```

Seed

```java
 Mono<Void> deleteUsers = userRepository.deleteAll();
        deleteUsers.subscribe();
        Mono<Void> deletePosts = postRepository.deleteAll();
        deletePosts.subscribe();

        User maria = new User(null, "Maria Brown ", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        Flux<User> insertUsers = userRepository.saveAll(Arrays.asList(maria, alex, bob));
        insertUsers.subscribe();

        maria = userRepository.searchEmail("maria@gmail.com").toFuture().get();
        alex = userRepository.searchEmail("alex@gmail.com").toFuture().get();
        bob = userRepository.searchEmail("bob@gmail.com").toFuture().get();

        Post post1 = new Post(null, Instant.parse("2022-11-21T18:35:24.00Z"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", maria.getId(), maria.getName());
        Post post2 = new Post(null, Instant.parse("2022-11-23T17:30:24.00Z"), "Bom dia", "Acordei feliz hoje!", maria.getId(), maria.getName());

        post1.addComment("Boa viagem mano!", Instant.parse("2022-11-21T18:52:24.00Z"), alex.getId(), alex.getName());
        post1.addComment("Aproveite!", Instant.parse("2022-11-22T11:35:24.00Z"), bob.getId(), bob.getName());
        post2.addComment("Tenha um ótimo dia!", Instant.parse("2022-11-23T18:35:24.00Z"), alex.getId(), alex.getName());

        post1.setUser(userRepository.searchEmail("maria@gmail.com").block());
        post2.setUser(userRepository.searchEmail("maria@gmail.com").block());

        Flux<Post> inserPosts = postRepository.saveAll(Arrays.asList(post1, post2));
        inserPosts.subscribe();
```
