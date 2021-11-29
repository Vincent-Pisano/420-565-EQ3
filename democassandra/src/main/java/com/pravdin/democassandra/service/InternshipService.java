package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.InternshipApplication;
import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.model.Monitor;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private InternshipApplicationRepository intersnhipApplicationRepository;

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
        List<InternshipOffer> internshipOfferList;
        List<InternshipOffer> internshipOfferListWaiting = new ArrayList<>();
        internshipOfferList = internshipOfferRepository.findAll();
        for (InternshipOffer internshipOffer : internshipOfferList){
            if (internshipOffer.getStatus().equals(InternshipOffer.OfferStatus.WAITING))
                internshipOfferListWaiting.add(internshipOffer);
        }
        return Optional.of(internshipOfferListWaiting);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String id){
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        if(optionalInternshipOffer.isPresent()){
            InternshipOffer internshipOffer = optionalInternshipOffer.get();
            internshipOffer.setStatus(InternshipOffer.OfferStatus.ACCEPTED);
            optionalInternshipOffer = Optional.of(internshipOfferRepository.save(internshipOffer));
            return optionalInternshipOffer;
        }

        return Optional.empty();
    }

    public Optional<InternshipOffer> refuseInternshipOffer(String id){
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        if(optionalInternshipOffer.isPresent()){
            InternshipOffer internshipOffer = optionalInternshipOffer.get();
            internshipOffer.setStatus(InternshipOffer.OfferStatus.REFUSED);
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
                InternshipOffer.OfferStatus isValidated = optionalInternshipOffer.get().getStatus();
                if(isValidated.equals(InternshipOffer.OfferStatus.ACCEPTED))
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
        List<InternshipOffer> internshipOfferList;
        List<InternshipOffer> internshipOfferListAccepted = new ArrayList<>();
        internshipOfferList = internshipOfferRepository.findAll();
        for (InternshipOffer internshipOffer : internshipOfferList){
            if (internshipOffer.getStatus().equals(InternshipOffer.OfferStatus.ACCEPTED))
                internshipOfferListAccepted.add(internshipOffer);
        }
        return Optional.of(internshipOfferListAccepted);
    }
}
