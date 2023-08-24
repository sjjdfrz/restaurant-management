package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.UserDto;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.security.RegisterRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(RegisterRequest dto, @MappingTarget User entity);


}
