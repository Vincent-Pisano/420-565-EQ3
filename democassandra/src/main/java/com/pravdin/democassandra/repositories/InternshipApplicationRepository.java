package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.InternshipApplication;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipApplicationRepository extends CassandraRepository<InternshipApplication, String> {

}
