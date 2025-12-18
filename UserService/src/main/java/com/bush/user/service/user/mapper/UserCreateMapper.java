package com.bush.user.service.user.mapper;

import com.bush.user.dto.UserCreateDto;
import com.bush.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserCreateMapper {
    @Mapping(target = "role", ignore = true)
    User mapToUser(UserCreateDto createDto);
}
