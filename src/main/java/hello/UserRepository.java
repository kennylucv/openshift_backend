package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}