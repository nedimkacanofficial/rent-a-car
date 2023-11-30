package com.rentacar.repository;

import com.rentacar.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.repository
 * @since 28/11/2023
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
