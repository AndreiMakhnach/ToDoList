package service;

import dto.UserDTO;
import mapper.UserMapper;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserById(Long id) {
        return userMapper.toDTO(userRepository.findById(id));
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    public void createUser(UserDTO userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

    public void updateUser(UserDTO userDTO) {
        userRepository.update(userMapper.toEntity(userDTO));
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
