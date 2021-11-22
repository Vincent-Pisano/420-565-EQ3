package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.InternshipApplication;
import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.model.Monitor;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private InternshipOfferRepository internshipOfferRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private IntersnhipApplicationRepository intersnhipApplicationRepository;

    public Optional<InternshipOffer> postInternshipOffer(InternshipOffer internshipOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try{
            Optional<Monitor> optionalMonitor = monitorRepository.findByUsername(internshipOffer.getMonitor());
            if(optionalMonitor.isPresent()){
                optionalInternshipOffer = Optional.of(internshipOfferRepository.save(internshipOffer));
            } else {
                return Optional.empty();
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalInternshipOffer;
    }

    public Optional<List<InternshipOffer>> getUnvalidatedInternshipOffers(){
        Optional<List<InternshipOffer>> optionalInternshipOfferList;
        optionalInternshipOfferList = internshipOfferRepository.getInternshipOffersByIsValidFalse();
        return optionalInternshipOfferList;
    }

    public Optional<InternshipOffer> validateInternshipOffer(String id){
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        if(optionalInternshipOffer.isPresent()){
            InternshipOffer internshipOffer = optionalInternshipOffer.get();
            internshipOffer.setIsValid(true);
            optionalInternshipOffer = Optional.of(internshipOfferRepository.save(internshipOffer));
            return optionalInternshipOffer;
        }

        return Optional.empty();
    }

    public Optional<InternshipApplication> postInternshipApplication(String student_id, String offer_id) {
        Optional<InternshipApplication> optionalInternshipApplication = Optional.empty();
        try{
            Optional<Student> optionalStudent = studentRepository.findById(student_id);
            Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(offer_id);
            if(optionalStudent.isPresent() && optionalInternshipOffer.isPresent()){
                Boolean isValidated = optionalInternshipOffer.get().getIsValid();
                if(isValidated)
                    optionalInternshipApplication = Optional.of(intersnhipApplicationRepository.save(
                        new InternshipApplication(student_id, offer_id)));
                else
                    return Optional.empty();
            } else {
                return Optional.empty();
            }
        } catch (DuplicateKeyException exception) {
            exception.printStackTrace();
        }
        return optionalInternshipApplication;
    }

    public Optional<List<InternshipOffer>> getValidatedInternshipOffers(){
        Optional<List<InternshipOffer>> optionalInternshipOfferList;
        optionalInternshipOfferList = internshipOfferRepository.getInternshipOffersByIsValidTrue();
        return optionalInternshipOfferList;
    }
}
