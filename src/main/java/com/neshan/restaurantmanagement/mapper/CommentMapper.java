package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.CommentDto;
import com.neshan.restaurantmanagement.model.dto.CommentRequestDto;
import com.neshan.restaurantmanagement.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);

    @Mapping(target = "username", source = "user.firstName")
    CommentDto commentToCommentDto(Comment comment);
}
