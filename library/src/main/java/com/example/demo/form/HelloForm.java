package com.example.demo.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel//让这个类可以被识别
@Data
public class HelloForm {
    private String namename;
    private String year;
    private String password;
}
