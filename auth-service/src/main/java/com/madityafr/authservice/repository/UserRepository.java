package com.madityafr.authservice.repository;

import com.madityafr.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "select count(*) from users u where \"role\" = 'user'", nativeQuery = true)
    Integer countClient();
}
