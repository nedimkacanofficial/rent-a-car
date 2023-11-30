package com.rentacar.repository;

import com.rentacar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.repository
 * @since 28/11/2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
