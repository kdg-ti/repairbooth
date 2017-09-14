package be.kdg.repaircafe.persistence.api;

import be.kdg.repaircafe.dom.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface to trigger proxy creation of CRUD Repository
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
 *
 * @author wouter
 */
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    User findUserByUsername(String username);
}
