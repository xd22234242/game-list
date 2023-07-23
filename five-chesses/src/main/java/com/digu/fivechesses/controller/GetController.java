package com.digu.fivechesses.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digu.fivechesses.entity.ChessBoard;
import com.digu.fivechesses.entity.User;
import com.digu.fivechesses.service.impl.UserServiceImpl;


@RestController
@RequestMapping("/get")
public class GetController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 通过 Id 获取用户属性
    @GetMapping("/getUserById")
    public User getUserById(Long id) {
        return userServiceImpl.getUserById(id);
    }

    // 通过用户名获取用户属性
    @GetMapping("/getUserByUsername")
    public User getUserByUsername(String userName) {
        return userServiceImpl.getUserByUserName(userName);
    }

    // 获取游玩匹配的人数
    @GetMapping("/redis/getUserNum")
    public int getUserNum() {
        int userNum = 0;
        if (!redisTemplate.hasKey("userNum")) {
            redisTemplate.opsForValue().set("userNum", "1");
            userNum = 1;
        } else {
            userNum = Integer.parseInt(redisTemplate.opsForValue().get("userNum"));
            userNum++;
            redisTemplate.opsForValue().set("userNum", String.valueOf(userNum));
        }
        return userNum;
    }

    // 游戏匹配人数 -1
    @GetMapping("/redis/deleteUserNum")
    public int deleteUserNum() {
        int userNum = 0;
        System.out.println("userNum:" + redisTemplate.hasKey("userNum"));
        userNum = Integer.parseInt(redisTemplate.opsForValue().get("userNum"));
        userNum--;
        redisTemplate.opsForValue().set("userNum", String.valueOf(userNum));
        return userNum;
    }

    // 判断游戏是否开始
    @GetMapping("/gameIsBegin")
    public boolean getPut() throws InterruptedException {
        // 参与人数
        int userNum = 0;

        do {
            userNum = Integer.parseInt(redisTemplate.opsForValue().get("userNum"));
            if (userNum == 0) {
                return false;
            }
            Thread.sleep(1000);
        }while (userNum % 2 != 0);
        
        redisTemplate.opsForValue().set("chessesType", "1");

        System.out.println("游戏开始");

        return true;
    } 

    // 判断是否能下棋 true表示可以，false表示不可以
    @GetMapping("/getPut") 
    public ChessBoard getPut(HttpServletRequest request) throws InterruptedException {
        while (!request.getParameter("chessesType")
        .equals(redisTemplate.opsForValue().get("chessesType"))) {
            Thread.sleep(1000);
        }
        
        return new ChessBoard(Integer.parseInt(redisTemplate.opsForValue().get("x")),
            Integer.parseInt(redisTemplate.opsForValue().get("y")), false, 0);
    }
}
