package spring_boot_security.dto;

import lombok.Data;
import spring_boot_security.model.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDTO {

    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 20,message = "Invalid input, name must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "LastName cannot be empty")
    @Size(min = 2, max = 20,message = "Invalid input, lastName must be between 2 and 20 characters")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 0, message = "Invalid input, age cannot be negative")
    private int age;

    @Size(min=2, message = "Invalid input, password must be min 2 characters")
    private String password;

    private Set<Role> roles;
}
