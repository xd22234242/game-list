package com.digu.fivechesses.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digu.fivechesses.entity.ChessBoard;
import com.digu.fivechesses.entity.User;
import com.digu.fivechesses.service.impl.UserServiceImpl;
import com.digu.fivechesses.service.impl.WinningConditionalServiceImpl;
import com.digu.fivechesses.util.StringToIntArr;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WinningConditionalServiceImpl winCondition;

    @PostMapping("/register")
    public User registerUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (userServiceImpl.existUserName(username)) {
            return null;
        } else {
            userServiceImpl.saveUser(new User(System.currentTimeMillis(), 
            username, request.getParameter("password")));
            return userServiceImpl.getUserByUserName(username);
        }
    }

    @PostMapping("/login")
    public User loginUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userServiceImpl.existUserName(username)) {
            if (userServiceImpl.getUserByUserName(username).getPassword().equals(password)) {
                return userServiceImpl.getUserByUserName(username);
            }
        }
        return null;
    }

    // 判断是否能下棋 true表示可以，false表示不可以
    @PostMapping("/putChess") 
    public ChessBoard putChess(HttpServletRequest request) throws InterruptedException {
        String str = request.getParameter("map");
        int[] map = (new StringToIntArr()).to(str);
        int x = Integer.parseInt(request.getParameter("x"));
        int y = Integer.parseInt(request.getParameter("y"));
        int chessType = Integer.parseInt(request.getParameter("chessesType"));
        boolean isTrue = winCondition.winnerConditionalScan(map, x, y, chessType);

        if (isTrue) {
            redisTemplate.opsForValue().set("chessesType", "3");
            return new ChessBoard(x, y, true, chessType);
        }

        redisTemplate.opsForValue().set("chessesType",
         String.valueOf(chessType == 1 ? 2 : 1));
        redisTemplate.opsForValue().set("x", String.valueOf(x));
        redisTemplate.opsForValue().set("y", String.valueOf(y));
        redisTemplate.opsForValue().set("map", str);

        while (!request.getParameter("chessesType")
        .equals(redisTemplate.opsForValue().get("chessesType"))) {
            if (("3").equals(redisTemplate.opsForValue().get("chessesType"))) {
                return new ChessBoard(x, y, true, 
                    Integer.parseInt(request.getParameter("chessesType")) == 1 ? 2 : 1);
            }
            Thread.sleep(1000);
        }

        x = Integer.parseInt(redisTemplate.opsForValue().get("x"));
        y = Integer.parseInt(redisTemplate.opsForValue().get("y"));
        chessType = Integer.parseInt(request.getParameter("chessesType"));

        return new ChessBoard(x, y, false, chessType);
    }
}
