package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Monitor;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitorRepository extends CassandraRepository<Monitor, String>{

    @AllowFiltering
    Optional<Monitor> findByUsernameAndPassword(String username, String password);

    Optional<Monitor> findByUsername(String username);

    Optional<Monitor> findByEmail(String email);
}
