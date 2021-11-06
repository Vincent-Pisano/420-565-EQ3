package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Supervisor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends CassandraRepository<Supervisor, String>{
}
