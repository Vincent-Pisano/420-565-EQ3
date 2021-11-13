package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Supervisor;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisorRepository extends CassandraRepository<Supervisor, String>{

    Optional<Supervisor> findByUsernameAndPassword(String username, String password);

    Optional<Supervisor> findByEmail(String email);
}
