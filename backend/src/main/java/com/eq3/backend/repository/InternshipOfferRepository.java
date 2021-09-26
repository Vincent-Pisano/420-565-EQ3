package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipOfferRepository extends MongoRepository<InternshipOffer, String> {

    List<InternshipOffer> findAllByWorkFieldAndIsValidTrue(Department workField);

}
