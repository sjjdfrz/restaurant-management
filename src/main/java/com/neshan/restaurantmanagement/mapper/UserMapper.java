package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.dto.UsersDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.model.dto.RegisterRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserMapper {

    UsersDto userToUsersDto(User user);

    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(RegisterRequest dto, @MappingTarget User user);
}
