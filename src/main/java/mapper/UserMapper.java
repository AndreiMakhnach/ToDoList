package mapper;

import dto.UserDTO;
import entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public User toEntity(UserDTO userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<User> toEntityList(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
