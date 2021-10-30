package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer>{
}
