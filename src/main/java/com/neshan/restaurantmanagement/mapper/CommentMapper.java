package com.neshan.restaurantmanagement.mapper;

import com.neshan.restaurantmanagement.model.dto.CommentDto;
import com.neshan.restaurantmanagement.model.dto.CommentRequestDto;
import com.neshan.restaurantmanagement.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);

    //Comment commentDtoToComment(CommentDto commentDto);
    CommentDto commentToCommentDto(Comment comment);



}
