package ues.elibrary.ebook.service.service.impl;

import ues.elibrary.ebook.entity.User;

import java.util.List;

public interface UserServiceImpl {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User save(User user);

    void remove(Long id);

}
