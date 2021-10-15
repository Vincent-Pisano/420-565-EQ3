package com.eq3.backend.repository;

import com.eq3.backend.model.Internship;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InternshipRepository extends MongoRepository<Internship, String> {

}
