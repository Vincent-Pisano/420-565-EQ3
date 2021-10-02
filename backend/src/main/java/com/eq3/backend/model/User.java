package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class User implements Serializable {

    @Id
    protected String idUser;
    @Field
    @Indexed(unique = true)
    protected String username;
    @Field
    protected String password;
    @Field
    @Indexed(unique = true)
    protected String email;
    @Field
    protected String firstName;
    @Field
    protected String lastName;
    @Field
    @Builder.Default
    protected Date creationDate = new Date();
    @Field
    @Builder.Default
    protected Boolean isDisabled = false;

    public User() {
        this.creationDate = new Date();
        this.isDisabled = false;
    }
}
