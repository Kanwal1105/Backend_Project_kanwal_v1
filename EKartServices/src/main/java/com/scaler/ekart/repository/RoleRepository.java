package com.scaler.ekart.repository;

import com.scaler.ekart.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    @Override
    Roles save(Roles o);

    List<Roles> findAllByIdIn(List<Long> roleIds);
}