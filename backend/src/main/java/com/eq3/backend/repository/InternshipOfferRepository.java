package com.eq3.backend.repository;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipOfferRepository extends MongoRepository<InternshipOffer, String> {

    @Query(value = "{'workField': ?0, 'session': ?1, 'status' : 'ACCEPTED', 'isDisabled':false}")
    List<InternshipOffer> findAllByWorkFieldAndSessionAndStatusAcceptedAndIsDisabledFalse(Department workField, String session);

    @Query(value = "{'status' : 'WAITING', 'isDisabled':false}")
    List<InternshipOffer> findAllByStatusWaitingAndIsDisabledFalse();

    @Query(value = "{'status' : 'ACCEPTED', 'isDisabled':false}")
    List<InternshipOffer> findAllByStatusAcceptedAndIsDisabledFalse();

    @Query(value = "{'status' : 'WAITING', 'isDisabled':false, 'session': ?0}")
    List<InternshipOffer> findAllByStatusWaitingAndIsDisabledFalseAndSession(String session);

    @Query(value = "{'status' : 'ACCEPTED', 'isDisabled':false, 'session': ?0}")
    List<InternshipOffer> findAllByStatusAcceptedAndIsDisabledFalseAndSession(String session);

    List<InternshipOffer> findAllBySessionAndMonitor_IdAndIsDisabledFalse(String session, String id);


}
