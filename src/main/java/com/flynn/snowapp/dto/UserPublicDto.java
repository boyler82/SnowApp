package com.flynn.snowapp.dto;

import com.flynn.snowapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPublicDto {
    private Long id;
    private String username;

    public static UserPublicDto from(User user) {
        return UserPublicDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
