package com.pravdin.democassandra.repositories;

import com.pravdin.democassandra.model.InternshipOffer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipOfferRepository extends CassandraRepository<InternshipOffer, String> {

    Optional<List<InternshipOffer>> getInternshipOffersByIsValidFalse();

    Optional<List<InternshipOffer>> getInternshipOffersByIsValidTrue();
}
