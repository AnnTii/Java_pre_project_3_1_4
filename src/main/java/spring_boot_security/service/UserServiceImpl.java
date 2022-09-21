package spring_boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_security.dao.RoleDao;
import spring_boot_security.dao.UserDao;
import spring_boot_security.dto.UserDTO;
import spring_boot_security.exceptionHandler.UserNotFoundException;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final Mapper modelMapper;

    @Autowired
    UserServiceImpl(UserDao userDao, RoleDao roleDao, Mapper modelMapper) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        completeUser(user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Email '%s' not found", email));
        }
        return user;
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private void completeUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
