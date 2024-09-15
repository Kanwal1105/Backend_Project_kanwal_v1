package com.scaler.ekart.services.Security;

import com.scaler.ekart.models.Roles;
import com.scaler.ekart.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles createRole(String name) {
        Roles role = new Roles();
        role.setRole(name);

        return roleRepository.save(role);
    }
}
