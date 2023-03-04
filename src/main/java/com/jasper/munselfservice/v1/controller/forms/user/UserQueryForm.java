package com.jasper.munselfservice.v1.controller.forms.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryForm {
    private Integer page;
    private Integer amount;
    private String uid;
    private String name;
    private String email;
    private String blocked;
    private String privileged;
}
