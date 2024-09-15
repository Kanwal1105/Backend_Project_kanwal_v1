package com.scaler.ekart.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@JsonDeserialize(as = Roles.class)
public class Roles extends BaseModel {
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> user;
}

