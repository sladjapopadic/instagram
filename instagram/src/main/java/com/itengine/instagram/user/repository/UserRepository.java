package com.itengine.instagram.user.repository;

import com.itengine.instagram.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);
}
