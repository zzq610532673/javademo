package com.sso.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor //无参构造器
@AllArgsConstructor//全参构造器
@Accessors(chain=true)//做链式调用 例如：new User.setId()
public class User {
    private Integer id;
    private String username;
    private String password;
}
