package com.bush.user.service.user.mapper;

import com.bush.user.dto.UserReadDto;
import com.bush.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserReadMapper {
    @Mapping(target = "role", source = "role.roleName")
    UserReadDto mapToUserReadDto(User user);
}
