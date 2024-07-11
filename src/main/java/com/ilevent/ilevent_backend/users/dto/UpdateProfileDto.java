package com.ilevent.ilevent_backend.users.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateProfileDto implements Serializable  {
    private String name;
    private String phone;
    private String picture;
}
