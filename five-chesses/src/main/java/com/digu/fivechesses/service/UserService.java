package com.digu.fivechesses.service;

import com.digu.fivechesses.entity.User;

public interface UserService {
    public User getUserById(Long id);

    // 判断用户是否存在
    public boolean existUserName(String userName);

    // 通过用户名返回用户实例
    public User getUserByUserName(String userName);

    // 插入用户数据
    public void saveUser(User user);

    // 通过用户名更新用户密码
    public void updatePasswordByUsername(String username, String newPassword);
}
