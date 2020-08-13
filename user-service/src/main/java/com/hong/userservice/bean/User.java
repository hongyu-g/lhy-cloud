package com.hong.userservice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author liang
 * @description
 * @date 2020/7/10 18:32
 */
@Entity(name = "user")
@Data
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

}
