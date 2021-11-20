package com.pravdin.democassandra.service;

import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.model.Monitor;
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

    public Optional<InternshipOffer> postInternshipOffer(InternshipOffer internshipOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = Optional.empty();
        try{
            if(!optionalInternshipOffer.isPresent()) {
                Optional<Monitor> optionalMonitor = monitorRepository.findByUsername(internshipOffer.getMonitor());
                if(optionalMonitor.isPresent()){
                    optionalInternshipOffer = Optional.of(internshipOfferRepository.save(internshipOffer));
                } else {
                    return Optional.empty();
                }
            }
            else {
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
}
