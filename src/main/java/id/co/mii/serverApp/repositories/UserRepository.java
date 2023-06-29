package id.co.mii.serverApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmployee_Email(String username, String email);

    public User findByUsername(String username);

    @Query("DELETE FROM User r WHERE r.id = ?1")
    public User deleteUser(Integer id);
}
