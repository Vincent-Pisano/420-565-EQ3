package com.eq3.backend.repository;

import com.eq3.backend.model.Internship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipRepository extends MongoRepository<Internship, String> {

}
