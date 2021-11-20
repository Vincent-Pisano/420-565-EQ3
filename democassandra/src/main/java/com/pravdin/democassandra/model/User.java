package com.pravdin.democassandra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    @Indexed
    protected String username;

    @Indexed
    protected String password;

    @Indexed
    protected String email;

    protected String firstName;
    protected String lastName;
}
