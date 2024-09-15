package com.scaler.ekart.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Session extends BaseModel {
    private String token;
    private Date expiringAt;
    @ManyToOne
    private Users user;
    @Enumerated(EnumType.ORDINAL)
    private SessionsStatus sessionStatus;
}
