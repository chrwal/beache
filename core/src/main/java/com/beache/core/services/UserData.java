package com.beache.core.services;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserData {

    private String name;
    private String email;

}
