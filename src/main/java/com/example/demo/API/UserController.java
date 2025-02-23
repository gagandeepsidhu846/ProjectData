package com.example.demo.API;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/saveUser")
    public ResponseEntity<UserEntity>saveUser(@RequestBody UserEntity userEntity){
        UserEntity saveUser = userService.saveUser(userEntity);
        ResponseEntity<UserEntity> response = ResponseEntity.status(HttpStatus.CREATED)
                .header("userName", "pragra123")
                .header("password", "pragra1234")
                .body(saveUser);
        return response;
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable int id, HttpServletResponse httpServletResponse){
        Optional<UserEntity> getUser = userService.getUser(id);
        ResponseEntity<Optional<UserEntity>> responseUser = ResponseEntity.status(HttpStatus.OK)
                .header("userName", "userOk")
                .header("password", "pass123")
                .body(getUser);
    return responseUser;
    }
    @GetMapping("/getUserName/{name}")
    public ResponseEntity<Optional<UserEntity>>getUserBYName(@PathVariable String name){
        Optional<UserEntity> userByName = userService.getUserByName(name);
        ResponseEntity<Optional<UserEntity>> response = ResponseEntity.status(HttpStatus.OK)
                .header("userName", "userOk")
                .header("password", "pass123")
                .body(userByName);
        return response;
    }
    //Delete ById

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<UserEntity>deleteById(@PathVariable int id){
        UserEntity deleteByID = userService.deleteByID(id);
        ResponseEntity<UserEntity> deleteResponse = ResponseEntity.status(HttpStatus.resolve(200))
                .header("userName", "user123")
                .header("password", "pass1234")
                .body(deleteByID);
        return deleteResponse;
    }
    @DeleteMapping("/deleteUserByName/{userName}")
    public ResponseEntity<UserEntity>deleteByName(@PathVariable String userName){
        UserEntity deleteByName = userService.deleteByName(userName);
        ResponseEntity<UserEntity> deleteResponse = ResponseEntity.status(HttpStatus.OK)
                .header("userName", "user123")
                .header("password", "pass124")
                .body(deleteByName);
        return deleteResponse;
    }
    //Update Existing userData By Id
    @PutMapping("/userUpdateById/{Id}")
    public ResponseEntity<UserEntity>updateById(@PathVariable int Id,@RequestBody UserEntity userEntity){
        UserEntity updateBYId = userService.updateBYId(Id, userEntity);
        ResponseEntity<UserEntity> response = ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "updateById")
                .body(updateBYId);
        return response;
    }
    //Update Existing user by Name
    @PutMapping("/userUpdateByName/{name}")
    public ResponseEntity<UserEntity> updateByName(@PathVariable String name,@RequestBody UserEntity userEntity){
        UserEntity updateBYName = userService.updateBYName(name, userEntity);
        ResponseEntity<UserEntity> response = ResponseEntity.status(HttpStatus.OK)
                .header("userName", "user123")
                .body(updateBYName);
        return response;
    }
    //Exception handler if exception occur
   @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String>noDataFound(NoSuchElementException exception)
   {
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(exception.getMessage());
   }


}
