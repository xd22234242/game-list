package com.digu.fivechesses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digu.fivechesses.dao.UserRepository;
import com.digu.fivechesses.entity.User;
import com.digu.fivechesses.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean existUserName(String userName) {
        return (userRepository.existUserName(userName) > 0 ? true : false);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePasswordByUsername(String username, String newPassword) {
        userRepository.updatePasswordByUsername(username, newPassword);
    }
}
