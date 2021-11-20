package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Student;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentRepository extends CassandraRepository<Student, String> {

    @AllowFiltering
    Optional<Student> findByUsernameAndPassword(String username, String password);

    Optional<Student> findByEmail(String email);
}
