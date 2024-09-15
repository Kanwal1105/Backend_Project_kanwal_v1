package com.scaler.ekart.controller.security;

import com.scaler.ekart.dtos.security.CreateRoleRequestDto;
import com.scaler.ekart.models.Roles;
import com.scaler.ekart.services.Security.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/addRoles")
    public ResponseEntity<Roles> createRole(CreateRoleRequestDto request) {
        Roles role = roleService.createRole(request.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
