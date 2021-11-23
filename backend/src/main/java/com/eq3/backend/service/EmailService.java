package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.InternshipApplicationRepository;
import com.eq3.backend.repository.InternshipManagerRepository;
import com.eq3.backend.repository.InternshipOfferRepository;
import com.eq3.backend.repository.InternshipRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.*;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRepository internshipRepository;
    private final InternshipManagerRepository internshipManagerRepository;

    EmailService(JavaMailSender mailSender,
                 InternshipApplicationRepository internshipApplicationRepository,
                 InternshipRepository internshipRepository,
                 InternshipManagerRepository internshipManagerRepository) {
        this.mailSender = mailSender;
        this.internshipApplicationRepository =internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
        this.internshipManagerRepository = internshipManagerRepository;
    }

    public void sendEmailWhenStudentAppliesToNewInternshipOffer(Student student, InternshipOffer offer) {
        Optional<InternshipManager> optionalManager = internshipManagerRepository.findByIsDisabledFalse();
        if (optionalManager.isPresent()) {
            InternshipManager manager = optionalManager.get();
            try {
                generateEmailWhenStudentAppliesToNewInternshipOffer(student, offer, manager);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "0 * * * * *")
    void sendMails(){
        Optional<InternshipManager> optionalManager = internshipManagerRepository.findByIsDisabledFalse();
        if (optionalManager.isPresent()) {
            InternshipManager manager = optionalManager.get();
            sendEmailToGSWhenStudentGetsInterviewed(manager);
            sendEmailToMonitorAboutEvaluation();
            sendEmailToSupervisorAboutEvaluation();
            sendEmailToStudentAboutInterviewOneWeekBefore();
        }
    }

    private void sendEmailToGSWhenStudentGetsInterviewed(InternshipManager manager) {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE)).minusMinutes(1);
        ZonedDateTime tomorrow = today.plusDays(1);
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findByInterviewDateBetweenAndIsDisabledFalse(Date.from(today.toInstant()), Date.from(tomorrow.toInstant()));
        internshipApplications.forEach(internshipApplication -> {
            try {
                generateEmailWhenStudentsGetsInterviewed(manager, internshipApplication);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendEmailToMonitorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse();
        internships.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();
            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of(UTC_TIME_ZONE)).minusDays(14).plusMinutes(1);
            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    generateEmailForMonitorAboutEvaluation(currentStudent, currentMonitor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendEmailToSupervisorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse();
        internships.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();

            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            Supervisor currentSupervisor = currentStudent.getSupervisorMap().get(currentOffer.getSession());
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of(UTC_TIME_ZONE)).minusDays(14).plusMinutes(1);

            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    generateEmailForSupervisorAboutEvaluation(currentSupervisor, currentMonitor);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendEmailToStudentAboutInterviewOneWeekBefore() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE)).plusDays(7).minusMinutes(1);
        ZonedDateTime tomorrow = today.plusDays(1);
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findByInterviewDateBetweenAndIsDisabledFalse(Date.from(today.toInstant()), Date.from(tomorrow.toInstant()));
        internshipApplications.forEach(internshipApplication -> {
            Student currentStudent = internshipApplication.getStudent();
            InternshipOffer currentInternshipOffer = internshipApplication.getInternshipOffer();
            try {
                generateEmailToStudentAboutInterviewOneWeekBefore(currentInternshipOffer, currentStudent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void generateEmailWhenStudentAppliesToNewInternshipOffer(Student student, InternshipOffer offer, InternshipManager manager) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.addTo(manager.getEmail());
        helper.setSubject("Un étudiant vient d'appliquer à une offre");
        helper.setText("L'étudiant " + student.getFirstName() + " " + student.getLastName() + " vient d'appliquer à l'offre : " + "\n" + offer.getJobName() + "\n" + offer.getDescription());

        mailSender.send(message);
    }

    private void generateEmailToStudentAboutInterviewOneWeekBefore(InternshipOffer internshipOffer, Student student) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(student.getEmail());
        helper.setSubject(EMAIL_SUBJECT_INTERVIEW_ONE_WEEK_BEFORE_STUDENT);
        helper.setText(getEmailTextStudentAboutInterviewOneWeekBefore(internshipOffer));
        mailSender.send(message);
    }

    private void generateEmailForSupervisorAboutEvaluation(Supervisor currentSupervisor, Monitor currentMonitor) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(currentSupervisor.getEmail());
        helper.setSubject(EMAIL_SUBJECT_SUPERVISOR_ABOUT_EVALUATION);
        helper.setText(getEmailTextForSupervisorAboutEvaluation(currentSupervisor, currentMonitor));
        mailSender.send(message);
    }


    private void generateEmailForMonitorAboutEvaluation(Student currentStudent, Monitor currentMonitor) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(currentMonitor.getEmail());
        helper.setSubject(EMAIL_SUBJECT_MONITOR_ABOUT_EVALUATION);
        helper.setText(getEmailTextForMonitorAboutEvaluation(currentStudent, currentMonitor));
        mailSender.send(message);
    }

    private void generateEmailWhenStudentsGetsInterviewed(InternshipManager manager, InternshipApplication internshipApplication) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Student currentStudent = internshipApplication.getStudent();
        helper.addTo(manager.getEmail());
        helper.setSubject(EMAIL_SUBJECT_INTERVIEW_STUDENT);
        helper.setText(getEmailTextWhenStudentsGetsInterviewed(currentStudent));
        mailSender.send(message);
    }


    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
    static
    class SchedulingConfiguration {

    }
}
