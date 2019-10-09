package ues.elibrary.ebook.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.elibrary.ebook.dto.UserDTO;
import ues.elibrary.ebook.entity.Role;
import ues.elibrary.ebook.entity.User;
import ues.elibrary.ebook.service.UserService;


import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userService.findAll();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));

        }
        return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/adduser")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {

        User existUser= userService.findByUsername(userDTO.getUsername());
        if (existUser == null) {
            User user = new User();
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(Role.PRETPLATNIK);
            userService.save(user);
        }else {
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully added!", HttpStatus.OK);
    }

    @PostMapping(value = "/change")
    public ResponseEntity<?> change(@RequestBody UserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setPassword(userDTO.getPassword());

        userService.save(user);
        return new ResponseEntity<String>("Successfully change!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        userService.remove(userId);

        return new ResponseEntity<String>("Successfully deleted!", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpSession session) {
        User user = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

        if(user != null) {
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<String>("Successfully logged in!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad credentials", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        return new ResponseEntity<String>("Successfully logged out!", HttpStatus.OK);
    }

    @GetMapping(value = "/loaduser")
    public ResponseEntity<?> loadUser(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if(userId == null) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
        User user = userService.findById(userId);
        UserDTO userDTO = new UserDTO(user);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }



}