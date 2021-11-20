package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.InternshipManager;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternshipManagerRepository extends CassandraRepository<InternshipManager, String> {

    @AllowFiltering
    Optional<InternshipManager> findByUsernameAndPassword(String username, String password);
}
