package com.scaler.ekart.repository;

import com.scaler.ekart.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Override
    Users save(Users o);

    List<Users> findAll();

    Optional<Users> findByEmail(String email);

}
