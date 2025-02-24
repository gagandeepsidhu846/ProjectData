package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;

import com.example.demo.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDTO saveUser(UserDTO userDTO) {

        User userEntity = new User(null, userDTO.getUserName(), userDTO.getUserEmail());

        User savedUser = userRepo.save(userEntity);

        return new UserDTO(savedUser.getUserId(), savedUser.getUserName(), savedUser.getUserEmail());
    }


    public Optional<UserDTO> getUser(Long id) {
        return userRepo.findById(id).map(user -> new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail()));
    }

    public Optional<UserDTO> getUserByName(String name) {
        return userRepo.findByUserName(name).map(user -> new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail()));
    }

    // Delete user by ID
    public void deleteByID(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NoSuchElementException("No user found with ID: " + id);
        }
        userRepo.deleteById(id);
    }

    // Delete user by name
    @Transactional
    public void deleteByName(String userName) {
        Optional<User> user = userRepo.findByUserName(userName);
        if (user.isEmpty()) {
            throw new NoSuchElementException("No user found with name: " + userName);
        }
        userRepo.deleteByUserName(userName);
    }

    // Update user by ID
    public UserDTO updateById(Long id, UserDTO userDTO) {
        return userRepo.findById(id)
                .map(user -> {
                    user.setUserName(userDTO.getUserName());
                    user.setUserEmail(userDTO.getUserEmail());
                    userRepo.save(user);
                    return new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail());
                }).orElseThrow(() -> new NoSuchElementException("No user found with ID: " + id));
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepo.findAllUsers()
                .stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail()))
                .collect(Collectors.toList());
    }
}
