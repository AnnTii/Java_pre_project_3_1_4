package spring_boot_security.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring_boot_security.SpringBootSecurityDemoApplication;
import spring_boot_security.dao.RoleDao;
import spring_boot_security.dao.UserDao;
import spring_boot_security.dto.UserDTO;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("userServiceTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({
//        GlobalExtension.class,
//        PostProcessingExtension.class,
//        ConditionExtension.class,
//        ThrowableExtension.class
//        MockitoExtension.class,
        SpringExtension.class
})
@ContextConfiguration(classes = SpringBootSecurityDemoApplication.class)
public class UserServiceTest {

//    private static final User ADAM = new User(1, "Adam", "Smith", "adam@mail.ru", 11, "adamPassword", new HashSet<>(), LocalDateTime.now(), "www");
//    private static final User JAMES = new User(2, "James", "Farmer", "james@mail.ru", 22, "jamesPassword", new HashSet<>(), LocalDateTime.now(), "www");
//    private static final User USER = new User(
//            1,"JustName", "JustLastName", "just@mail.ru", 11, "justpassword", new HashSet<>(), LocalDateTime.now(),"My"
//    );
//    private static final UserDTO DTO = new UserDTO(
//            2,"DTOname", "DTOlastName", "dto@mail.ru", 22, "dtopassword", new HashSet<>()
//    );
//
//    static Stream<Arguments> getArgumentsForLoginTest() {
//        return Stream.of(
//                 Arguments.of("adam@mail.ru", "adamPassword", Optional.of(ADAM)),
//                 Arguments.of("james@mail.ru", "jamesPassword", Optional.of(JAMES))
//        );
//    }
//
////    @InjectMocks
//    @Mock
//    UserServiceImpl userServiceImpl;
//    @Mock
//    UserDao userDao;
//    @Mock
//    RoleDao roleDao;
//    @Mock
//    Mapper modelMapper;
//    @Captor
//    ArgumentCaptor<User> userArgumentCaptor;
//
////    @Autowired
////    public UserServiceTest(UserService userService) {
////        this.userService = userService;
////    }
//
//
//    @BeforeAll
//    void beforeAllTest() {
//        System.out.println("Before All : " + this);
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        System.out.println("Before Each : " + this);
////        this.userDao = Mockito.mock(UserDao.class);
////        this.roleDao = Mockito.mock(RoleDao.class);
////        this.modelMapper = Mockito.mock(Mapper.class);
////        this.userService = new UserServiceImpl(userDao, roleDao, modelMapper);
//    }
//
//    @Test
//    @Tag("noChangeBD")
//    @Order(1)
//    @DisplayName("Тест получить всех users")
//    void usersGetTest() {
//        System.out.println("Test 1 : " + this);
//        List<User> allUsers = userDao.findAll();
//        assertThat(allUsers).isNotEmpty();
//        System.out.println(allUsers);
//    }
//
//    @Test
//    @Tag("noChangeBD")
//    @Order(2)
//    @DisplayName("Тест получить все roles")
//    void rolesGetTest() {
//        System.out.println("Test 2 : " + this);
//        List<Role> allRoles = roleDao.findAll();
//        assertThat(allRoles).isNotEmpty();
//        System.out.println(allRoles);
//    }
//
//    @Test
//    @Tag("withChangeBD")
//    @Order(3)
//    @DisplayName("Тест добавить user в DB")
//    void usersAddTest() {
//        System.out.println("Test 3 : " + this);
//        userDao.save(ADAM);
////        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        Mockito.verify(userDao, Mockito.times(1)).save(userArgumentCaptor.capture());
//        assertThat(userArgumentCaptor.getValue()).isEqualTo(ADAM);
////        assertThat(userService.getAllUsers()).hasSize(5);
//    }
//
////    @Test
////    @DisplayName("Тест mockito")
////    void mockitoTest() {
////        System.out.println("Test mockito : " + this);
////        Mockito.doReturn(true).when(userDao).save(ADAM);
////        Mockito.doReturn(true).when(userDao).save(JAMES);
////        userService.saveUserForTest(ADAM);
////        userService.saveUserForTest(JAMES);
//////        assertThat(userService.getAllUsers()).hasSize(5);
////    }
//
//    @Test
//    @Order(4)
//    @DisplayName("Тест проверки поиска user по id")
//    void getUserByIdTest() {
//        System.out.println("Test 4 : " + this);
////        assertThat(userDao.findById(ADAM.getId())).contains(ADAM);
////        assertThat(userServiceImpl.findUserById(ADAM.getId())).matches(user -> user.getId().equals(ADAM.getId()));
//
////        Mockito.doReturn(Optional.of(ADAM)).when(userServiceImpl.findUserByIdTest(ADAM.getId()));
//        Mockito.when(userServiceImpl.findUserById(ADAM.getId())).thenReturn(ADAM);
//        assertThat(userServiceImpl.findUserById(ADAM.getId())).isEqualTo(ADAM);
//
////        assertThat(userDao.findById(ADAM.getId())).isEqualTo(ADAM.getId());
//
////        Mockito.doReturn(Optional.of(new User())).when(userDao).findById(ADAM.getId());
//
////        Optional<User> userResult = userServiceImpl.findUserByIdTest(ADAM.getId());
//
////        assertTrue(userResult.isPresent());
//
//
//    }
//
//    @Test
//    @Tag("withChangeBD")
//    @Order(5)
//    @DisplayName("Тест конвертировать users в map")
//    void usersConvertedToMapByIdTest() {
//        System.out.println("Test 5 : " + this);
//        Map<Integer, User> mapUsers = userDao.findAll().stream().collect(Collectors.toMap(User::getId, Function.identity()));
//        assertAll(
//                () -> assertThat(mapUsers).containsKeys(ADAM.getId(), JAMES.getId()),
//                () -> assertThat(mapUsers).containsValues(ADAM, JAMES)
//        );
//    }
//
//    @Test
//    @Tag("converter")
//    @Order(6)
//    @DisplayName("Тест конвертировать userDTO в user")
//    void convertDtoToUserTest() {
//        System.out.println("Test 6 : " + this);
//        System.out.println(DTO);
//        assertThat(userServiceImpl.convertToUser(DTO)).isNotNull();
//        User user = userServiceImpl.convertToUser(DTO);
//        System.out.println(user);
//    }
//
//    @Test
//    @Tag("converter")
//    @Order(7)
//    @DisplayName("Тест конвертировать user в userDTO")
//    void convertUserToDtoTest() {
//        System.out.println("Test 7 : " + this);
//        System.out.println(USER);
//        assertThat(userServiceImpl.convertToUserDTO(USER)).isNotNull();
//        UserDTO userDTO = userServiceImpl.convertToUserDTO(USER);
//        System.out.println(userDTO);
//    }
//
//    @Tag("noChangeBD")
//    @Order(8)
//    @DisplayName("Тест логин - поиск user с логином и паролем")
//    @ParameterizedTest
//    @MethodSource("spring_boot_security.service.UserServiceTest#getArgumentsForLoginTest")
//    void loginTest(String email, String password, Optional<User> user) {
//        System.out.println("Test 8 : " + this);
//        Optional<User> userOptional = userServiceImpl.loginTest(email, password);
//        assertThat(userOptional).isEqualTo(user);
//    }
//
//    @Test
//    @Tag("noChangeBD")
//    @Order(9)
//    @DisplayName("Тест производительности на loginTest")
//    void checkLoginTestPerformanceFunctionality() {
//        System.out.println("Test 9 : " + this);
////        Optional<User> optionalUser = assertTimeoutPreemptively(Duration.ofMillis(200L), () -> {
////            System.out.println(Thread.currentThread().getName());
////            Thread.sleep(100L);
////            return userService.loginTest(ADAM.getEmail(), ADAM.getPassword());
////        });
//        assertTimeoutPreemptively(Duration.ofMillis(100L), () -> userServiceImpl.loginTest(ADAM.getEmail(), ADAM.getPassword()));
//    }
//
//    @Test
//    @Tag("withChangeBD")
//    @Order(10)
//    @DisplayName("Тест удаление user из DB")
//    void deleteUserTest() {
//        System.out.println("Test 10 : " + this);
//        userDao.deleteUserByName(ADAM.getName());
////        userService.deleteUserByNameForTest(JAMES.getName());
//        assertAll(
//               () ->  assertThat(userDao.findAll().stream().filter(user -> user.getName().equals("Adam")).findAny()).isEmpty()
////               () ->  assertThat(userService.getAllUsers().stream().filter(user -> user.getName().equals("James")).findAny()).isEmpty()
//        );
//        Mockito.verify(userDao, Mockito.times(1)).deleteUserByName(ADAM.getName());
//    }
//
//    @AfterEach
//    void afterEach() {
//        System.out.println("After Each : " + this);
//    }
//
//    @AfterAll
//    void afterAll() {
//        System.out.println("After All : " + this);
//    }
}
