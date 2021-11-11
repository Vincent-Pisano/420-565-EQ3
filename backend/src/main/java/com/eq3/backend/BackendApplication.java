package com.eq3.backend;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.repository.InternshipApplicationRepository;
import com.eq3.backend.repository.InternshipOfferRepository;
import com.eq3.backend.service.InternshipService;
import com.mongodb.client.DistinctIterable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    /*private final MongoTemplate mongoTemplate;
    private final InternshipOfferRepository internshipOfferRepository;

    public BackendApplication(InternshipOfferRepository internshipOfferRepository, MongoTemplate mongoTemplate) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.mongoTemplate = mongoTemplate;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    public void run(String... args) {
        /*Query query = new Query(
                Criteria.where("monitor.$id")
                        .is(new ObjectId("6164accb3418286e9469714a"))
        );

        List<String> coll = mongoTemplate
                .getCollection("internshipOffer")
                .distinct("session", query.getQueryObject() ,String.class)
                .into(new ArrayList<>());

        Collections.reverse(coll);

        coll.forEach(System.out::println);*/
    }
}
