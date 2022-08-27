package br.com.springboot.springboot.controllers;

import br.com.springboot.springboot.exception.ResourceNotFoundException;
import br.com.springboot.springboot.model.User;
import br.com.springboot.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public User user() {
        User user = new User(1L, "Diego", "diego.czajka");

        return user;
    }

    @PostMapping("/")
    public User user(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @GetMapping("/list")
    public List<User> list() {
        return this.userRepository.findAll();
    }
    @GetMapping("/list/{id}")
    public List<User> listMoreThan(@PathVariable("id") Long id) {
        return this.userRepository.findByIdGreaterThan(id);
    }

    @GetMapping("/{id}")
    public User user(@PathVariable("id") Long id) {
        Optional<User> userFind = this.userRepository.findById(id);

        if (userFind.isPresent()) {
            return userFind.get();
        }

        return null;

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
         User updateUser = this.userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User does not exists with id: " + id));
        updateUser.setName(userDetails.getName());
        updateUser.setUsername(userDetails.getUsername());

        this.userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User updateUser = this.userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User does not exists with id: " + id));

        this.userRepository.deleteById(id);
        return  ResponseEntity.ok("User successeful deleted" + id);
    }

}
