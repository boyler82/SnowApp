package com.flynn.snowapp.dto;

import com.flynn.snowapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrivateDto {
    private Long id;
    private String username;
    private String email;

    public static UserPrivateDto from(User user) {
        return UserPrivateDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
