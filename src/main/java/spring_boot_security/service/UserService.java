package spring_boot_security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import spring_boot_security.dto.UserDTO;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    List<Role> getAllRoles();
    void saveUser(User user);
    void deleteUserById(int id);
    User findUserById(int id);
    User convertToUser(UserDTO userDTO);
    UserDTO convertToUserDTO(User user);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void saveUserForTest(User user);
    void deleteUserByNameForTest(String username);
    Optional<User> loginTest(String email, String password);
    Optional<User> findUserByIdTest(int id);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
