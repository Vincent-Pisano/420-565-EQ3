package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.InternshipOffer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipOfferRepository extends CassandraRepository<InternshipOffer, String> {
}
