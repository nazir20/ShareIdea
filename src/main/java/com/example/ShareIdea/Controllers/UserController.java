package com.example.ShareIdea.Controllers;

import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }


    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User newUser){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundedUser = user.get();
            foundedUser.setUsername(newUser.getUsername());
            foundedUser.setPassword(newUser.getPassword());
            userRepository.save(foundedUser);
            return foundedUser;
        }else{
            return null;
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
    }




}
