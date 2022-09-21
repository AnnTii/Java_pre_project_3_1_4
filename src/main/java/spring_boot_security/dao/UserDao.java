package spring_boot_security.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_security.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    User getUserByEmail(String email);
}
