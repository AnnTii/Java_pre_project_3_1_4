package spring_boot_security.service;

import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        log.info("IN UserServiceImpl getAllUsers");
        return userDao.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("IN UserServiceImpl getAllRoles");
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        completeUser(user);
        log.info("IN UserServiceImpl saveUser {}", user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        log.info("IN UserServiceImpl deleteUserById {}", id);
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("IN UserServiceImpl loadUserByUsername {}", email);
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Email '%s' not found", email));
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        log.info("IN UserServiceImpl findUserById {}", id);
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private void completeUser(User user) {
        log.info("IN UserServiceImpl completeUser(setCreatedAt) {}", user);
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedAt(LocalDateTime.of(
//                LocalDate.now().getYear(),
//                LocalDate.now().getMonth(),
//                LocalDate.now().getDayOfMonth(),
//                LocalTime.now().getHour(),
//                LocalTime.now().getMinute()
//        ));
        log.info("IN UserServiceImpl completeUser(setCreatedBy) {}", user);
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public User convertToUser(UserDTO userDTO) {
        log.info("IN UserServiceImpl convertToUser {}", userDTO);
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
        log.info("IN UserServiceImpl convertToUserDTO {}", user);
        return modelMapper.map(user, UserDTO.class);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void saveUserForTest(User user) {
        log.info("IN UserServiceImpl saveUserForTest {}", user);
        userDao.save(user);
    }

    @Override
    public void deleteUserByNameForTest(String username) {
        log.info("IN UserServiceImpl deleteUserByNameForTest {}", username);
        userDao.deleteUserByName(username);
    }

    @Override
    public Optional<User> loginTest(String email, String password) {
        log.info("IN UserServiceImpl loginTest {} {}", email, password);
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public Optional<User> findUserByIdTest(int id) {
        log.info("IN UserServiceImpl findUserByIdTest {}", id);
        return Optional.ofNullable(userDao.findById(id).orElseThrow(UserNotFoundException::new));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
