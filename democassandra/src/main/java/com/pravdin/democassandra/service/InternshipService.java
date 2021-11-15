package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private InternshipManagerRepository internshipManagerRepository;

    @Autowired
    private InternshipOfferRepository internshipOfferRepository;

    public Optional<InternshipOffer> postInternshipOffer(InternshipOffer internshipOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try{

            if(!optionalInternshipOffer.isPresent()) {
                optionalInternshipOffer = Optional.of(internshipOfferRepository.save(internshipOffer));
            }
            else {
                return Optional.empty();
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalInternshipOffer;
    }
}
