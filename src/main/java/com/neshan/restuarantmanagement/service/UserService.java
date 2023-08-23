package com.neshan.restuarantmanagement.service;

import com.neshan.restuarantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restuarantmanagement.model.dto.UserDto;
import com.neshan.restuarantmanagement.model.entity.User;
import com.neshan.restuarantmanagement.repository.UserRepository;
import com.neshan.restuarantmanagement.util.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private MapStructMapper mapStructMapper;

    public List<UserDto> getAllUsers() {

        return userRepository
                .findAll()
                .stream()
                .map(mapStructMapper::userToUserDto)
                .toList();
    }

    public UserDto getUserById(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        return mapStructMapper.userToUserDto(user);
    }

    public void createUser(UserDto userDto) {
        User user = mapStructMapper.userDtoToUser(userDto);
        userRepository.save(user);
    }

    public void updateUser(long id, UserDto userRequest) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

//        user.setTitle(UserRequest.title());
//        user.setDescription(UserRequest.description());
        userRepository.save(user);
    }

    public void deleteUser(long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The user with ID %d was not found.", id)));

        userRepository.delete(user);
    }
}
