package com.example.springboot.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrudUserForm {
    private Integer id;
    private String name;
    private String email;
    private String password;
}
