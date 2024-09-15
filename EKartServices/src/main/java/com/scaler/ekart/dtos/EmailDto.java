package com.scaler.ekart.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    public String to;
    public String subject;
    public String body;
    public String from;
}
