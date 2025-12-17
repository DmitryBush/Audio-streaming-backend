package com.bush.user.service.mapper;

import com.bush.user.dto.UserCreateDto;
import com.bush.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserCreateMapper {
    User mapToUser(UserCreateDto createDto);
}
