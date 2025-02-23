package com.example.demo.Service;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    //Create new user
    private UserRepo userRepo;

    public UserEntity saveUser(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    //GetExisting user
    public Optional<UserEntity> getUser(int id) {
        return userRepo.findById(id);
    }

    //GetExisting user By Name
    public Optional<UserEntity> getUserByName(String name) {
        return userRepo.getUserEntitiesByUserName(name);
    }

    //Delete User ById
    public UserEntity deleteByID(int id) {
     return userRepo.findById(id)
             .map(user->{
                 userRepo.deleteById(id);
                 return user;
             })
             .orElseThrow(()->new NoSuchElementException("No data has been found"));
}
//Delete User ByName
    @Transactional
    public UserEntity deleteByName(String userName){
       return userRepo.getUserEntitiesByUserName(userName)
                .map(user->{
                    userRepo.deleteByUserName(userName);
                    return user;
                })
               .orElseThrow(()->new NoSuchElementException("No data has been found"));
    }
    //update Data byUsing Id
    public UserEntity updateBYId(int Id,UserEntity userEntity){
       return userRepo.findById(Id)
               .map(user->{
                   user.setUserName(userEntity.getUserName());
                   user.setUserEmail(user.getUserEmail());
                   return userRepo.save(user);
        })
                .orElseThrow(()->new NoSuchElementException("No User has been found"));
    }
    //update User by userName
    public UserEntity updateBYName(String name,UserEntity userEntity){
        return getUserByName(name).map(user->{
            user.setUserName(userEntity.getUserName());
            user.setUserEmail(userEntity.getUserEmail());
            return userRepo.save(user);
        })
                .orElseThrow(()->new NoSuchElementException("No User has been found"));
    }
}