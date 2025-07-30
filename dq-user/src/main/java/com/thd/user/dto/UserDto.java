package com.thd.user.dto;

import com.thd.base.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String fullName;

    public UserDto(User user) {
        if(user!=null){
            this.username=user.getUsername();
            this.password=user.getPassword();
            this.email=user.getEmail();
            this.phoneNumber=user.getPhoneNumber();
            this.address=user.getAddress();
            this.fullName=user.getFullName();
        }
    }
}
