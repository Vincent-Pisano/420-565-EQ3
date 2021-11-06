package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Student;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CassandraRepository<Student, String> {
}
