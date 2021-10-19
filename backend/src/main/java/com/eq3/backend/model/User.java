package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User extends Entity {

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

    protected Binary signature;
}
