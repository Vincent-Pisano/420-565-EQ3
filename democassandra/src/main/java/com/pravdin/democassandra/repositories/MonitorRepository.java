package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.Monitor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends CassandraRepository<Monitor, String>{
}
