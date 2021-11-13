package com.pravdin.democassandra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User {

    @PrimaryKey
    protected String username;

    @Indexed
    protected String password;

    @Indexed
    protected String email;

    protected String firstName;
    protected String lastName;
}
