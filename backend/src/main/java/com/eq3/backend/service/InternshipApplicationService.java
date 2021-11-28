package com.eq3.backend.service;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.model.Student;
import com.eq3.backend.repository.InternshipApplicationRepository;
import com.eq3.backend.repository.InternshipOfferRepository;
import com.eq3.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eq3.backend.utils.Utils.getNextSessionFromDate;

@Service
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final StudentRepository studentRepository;
    private final EmailService emailService;

    InternshipApplicationService(InternshipApplicationRepository internshipApplicationRepository,
                                 InternshipOfferRepository internshipOfferRepository,
                                 StudentRepository studentRepository,
                                 EmailService emailService) {
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    public Optional<InternshipApplication> applyInternshipOffer(String studentUsername, InternshipOffer internshipOffer) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        return optionalStudent.map(student -> createInternshipApplication(student, internshipOffer));
    }

    private InternshipApplication createInternshipApplication(Student student, InternshipOffer internshipOffer){
        InternshipApplication internshipApplication = new InternshipApplication();
        internshipApplication.setInternshipOffer(internshipOffer);
        internshipApplication.setStudent(student);
        emailService.sendEmailWhenStudentAppliesToNewInternshipOffer(student, internshipOffer);
        return internshipApplicationRepository.save(internshipApplication);
    }

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudent = new ArrayList<>();

        optionalStudent.ifPresent(student -> {
            List<InternshipApplication> internshipApplications = internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(student);
            internshipApplications.forEach(internshipApplication -> {
                InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
                if (session.equals(internshipOffer.getSession())) {
                    internshipApplicationsOfStudent.add(internshipApplication);
                }
            });
        });

        return internshipApplicationsOfStudent.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudent);
    }

    public Optional<List<InternshipApplication>> getAllCompletedInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudentCompleted =
                optionalStudent
                        .map(student ->
                                getAllInternshipApplicationBySessionAndStatus(student, session, InternshipApplication.ApplicationStatus.COMPLETED))
                        .orElseGet(ArrayList::new);

        return internshipApplicationsOfStudentCompleted.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudentCompleted);
    }

    public Optional<List<InternshipApplication>> getAllWaitingInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudentWaiting =
                optionalStudent
                        .map(student ->
                                getAllInternshipApplicationBySessionAndStatus(student, session, InternshipApplication.ApplicationStatus.WAITING))
                        .orElseGet(ArrayList::new);

        return internshipApplicationsOfStudentWaiting.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudentWaiting);
    }

    private List<InternshipApplication> getAllInternshipApplicationBySessionAndStatus(Student student, String session, InternshipApplication.ApplicationStatus status) {
        List<InternshipApplication> internshipApplicationsOfStudent = new ArrayList<>();

        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStudentAndIsDisabledFalseAndStatus(student, status);
        internshipApplications.forEach(internshipApplication -> {
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            if (session.equals(internshipOffer.getSession())) {
                internshipApplicationsOfStudent.add(internshipApplication);
            }
        });
        return internshipApplicationsOfStudent;
    }

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfInternshipOffer(String id) {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByInternshipOffer_IdAndStatusIsNotAcceptedAndIsDisabledFalse(id);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.ACCEPTED);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllAcceptedInternshipApplicationsNextSessions() {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByStatusAcceptedAndIsDisabledFalseAndSession(getNextSessionFromDate(new Date()));
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByIsDisabledFalseAndInternshipOfferInAndStatus(
                        internshipOffers, InternshipApplication.ApplicationStatus.ACCEPTED
                );

        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllValidatedInternshipApplications() {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.VALIDATED);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferNotAppliedOfStudentBySession(String username, String session) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(username);

        List<InternshipOffer> internshipOffers = optionalStudent
                .map(student -> internshipOfferRepository
                        .findAllByWorkFieldAndSessionAndStatusAcceptedAndIsDisabledFalse(student.getDepartment(), session))
                .orElseGet(ArrayList::new);

        List<InternshipApplication> internshipApplications = optionalStudent
                .map(student -> internshipApplicationRepository.findAllByStudentAndIsDisabledFalseAndInternshipOfferIn(student, internshipOffers))
                .orElseGet(ArrayList::new);

        internshipApplications.stream().map(InternshipApplication::getInternshipOffer).forEach(internshipOffers::remove);

        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<InternshipApplication> updateInternshipApplication(InternshipApplication internshipApplication) {
        Optional<InternshipApplication> optionalInternshipApplication =
                internshipApplicationRepository.findById(internshipApplication.getId());

        return optionalInternshipApplication.map(_internshipApplication ->
                internshipApplicationRepository.save(internshipApplication));
    }

}
