package com.digu.fivechesses.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 用户

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @Column
    private String username;

    @Column
    private String password;    
}
