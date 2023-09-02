package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.CommentMapper;
import com.neshan.restaurantmanagement.model.dto.CommentDto;
import com.neshan.restaurantmanagement.model.dto.CommentRequestDto;
import com.neshan.restaurantmanagement.model.entity.Comment;
import com.neshan.restaurantmanagement.model.entity.Item;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.CommentRepository;
import com.neshan.restaurantmanagement.repository.ItemRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private ItemRepository itemRepository;

    @Transactional
    public List<CommentDto> getAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(comment -> commentMapper.commentToCommentDto(comment))
                .toList();
    }

    @Transactional
    public CommentDto getComment(long id) {

        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The comment with ID %d was not found.", id)));

        return commentMapper.commentToCommentDto(comment);
    }

    @Transactional
    public List<CommentDto> getAllCommentsOfItem(long itemId) {

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The item with ID %d was not found.", itemId)));

        return item
                .getComments()
                .stream()
                .map(comment -> commentMapper.commentToCommentDto(comment))
                .toList();
    }

    @Transactional
    public List<CommentDto> getAllCommentsOfUser(HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        return commentRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(comment -> commentMapper.commentToCommentDto(comment))
                .toList();
    }

    @Transactional
    public void addComment(long itemId, HttpServletRequest request, CommentRequestDto commentRequestDto) {

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The item with ID %d was not found.", itemId)));

        User user = (User) request.getAttribute("user");

        Comment comment = commentMapper.commentRequestDtoToComment(commentRequestDto);
        comment.setUser(user);
        item.addComment(comment);
        itemRepository.save(item);
    }

    @Transactional
    public void addCommentResponse(long commentId, CommentDto commentDto) {

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The comment with ID %d was not found.", commentId)));

        System.out.println(commentDto.response());
        comment.setResponse(commentDto.response());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
