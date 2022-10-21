package spring_boot_security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring_boot_security.dto.UserDTO;
import spring_boot_security.model.Role;
import spring_boot_security.model.User;
import spring_boot_security.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restApi")
@Tag(name = "Мой REST Controller")
public class RestApiController {

    private final UserService userService;

    @Autowired
    public RestApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Hidden - не добавлять запрос в документацию
//    @Operation(summary  = "Получить list users", tags = "Рест апи контроллер - получить list users")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "List users получен успешно"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    @Operation(summary  = "Получить list roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "List roles получен успешно"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary  = "Получить user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "User by id получен успешно"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<UserDTO> getUserById ( @PathVariable("id") int id) {
        return new ResponseEntity<>(userService.convertToUserDTO(userService.findUserById(id)), HttpStatus.OK);
    }

    @GetMapping("/authUser")
    @Operation(summary  = "Получить Authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Authenticated user получен успешно"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<User> getAuthorUser() {
        return new ResponseEntity<>((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary  = "Добавить нового user")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "Новый user успешно добавлен"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody @Valid UserDTO userDTO) {
        userService.saveUser(userService.convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping()
    @Operation(summary  = "Обновить user")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "User успешно обновлен"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<HttpStatus> editeUser(@RequestBody @Valid UserDTO userDTO) {
        userService.saveUser(userService.convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary  = "Удалить user")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description  = "User успешно удален"),
            @ApiResponse(responseCode  = "404", description  = "Ошибка в запросе")
    })
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User with ID = " + id + " was deleted", HttpStatus.OK);
    }

}
