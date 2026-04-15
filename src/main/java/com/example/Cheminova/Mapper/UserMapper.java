package com.example.Cheminova.Mapper;

import com.example.Cheminova.DTOs.Request.UpdateProfileRequest;
import com.example.Cheminova.DTOs.Response.UserResponse;
import com.example.Cheminova.Model.Users;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(Users user);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileToUser(UpdateProfileRequest request, @MappingTarget Users user);

}
