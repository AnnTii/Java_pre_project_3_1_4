package spring_boot_security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import spring_boot_security.dto.UserDTO;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    List<Role> getAllRoles();
    void saveUser(User user);
    void deleteUserById(int id);
    User findUserById(int id);
    User convertToUser(UserDTO userDTO);
    UserDTO convertToUserDTO(User user);
}
