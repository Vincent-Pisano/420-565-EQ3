package com.eq3.backend.utils;

import com.eq3.backend.model.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtilsTest {

    public final static String DOCUMENT_EXTENSION = "Evaluation.pdf";
    public final static String DOCUMENT_NAME = "student";

    public final static String PDF_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\documentTest.pdf";

    public final static String PDF_EVALUATION_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\studentEvaluation.pdf";

    public static Student getStudentWithId(){
        Student student = getStudentWithoutId();
        student.setId("61478hgk58e00c02c02bhd5");
        return student;
    }

    public static Student getStudentWithoutId(){
        return Student.builder()
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
    }

    public static List<Student> getListOfStudents(){
        Student student1 = getStudentWithId();
        Student student2 = getStudentWithId();
        List<Student> studentsList = new ArrayList();
        studentsList.add(student1);
        studentsList.add(student2);
        return studentsList;
    }

    public static Monitor getMonitorWithId(){
        Monitor monitor = getMonitorWithoutId();
        monitor.setId("61478hgk580000jbhd5");
        return monitor;
    }

    public static Monitor getMonitorWithoutId(){
        return Monitor.builder()
                .username("M1234313")
                .password("DAJo90l")
                .email("Nicolas.lavoie43@gmail.com")
                .firstName("Nicolas")
                .lastName("Lavoie")
                .jobTitle("Sales manager")
                .enterpriseName("Amazon")
                .build();
    }

    public static Supervisor getSupervisorWithId(){
        Supervisor supervisor = getSupervisorWithoutId();
        supervisor.setId("15848hgk58e00c02c02bhd5");
        return supervisor;
    }

    public static Supervisor getSupervisorWithoutId(){
        return Supervisor.builder()
                .username("S1298896")
                .password("JeA55E!")
                .email("jeanne.dumond@gmail.com")
                .firstName("Jeanne")
                .lastName("Dumond")
                .department(Department.NURSING)
                .build();
    }

    public static List<Supervisor> getListOfSupervisors(){
        Supervisor supervisor1 = getSupervisorWithId();
        Supervisor supervisor2 = getSupervisorWithId();
        List<Supervisor> supervisorsList = new ArrayList();
        supervisorsList.add(supervisor1);
        supervisorsList.add(supervisor2);
        return supervisorsList;
    }

    public static InternshipManager getInternshipManagerWithId(){
        InternshipManager internshipManager = getInternshipManagerWithoutId();
        internshipManager.setId("6146tf5eg8e00c02c02bhd5");
        return internshipManager;
    }

    public static InternshipManager getInternshipManagerWithoutId(){
        return InternshipManager.builder()
                .username("G42415")
                .password("qWeRtY987")
                .email("marcel.tremblay@outlook.com")
                .firstName("Marcel")
                .lastName("Tremblay")
                .build();
    }

    public static InternshipOffer getInternshipOfferWithId() {
        InternshipOffer internshipOffer = getInternshipOfferWithoutId();
        internshipOffer.setId("91448hkk58e00c02w02bjd4");
        return internshipOffer;
    }

    public static InternshipOffer getInternshipOfferWithoutId() {
        List<String> allWeekDay = new ArrayList<>();
        allWeekDay.add("Monday");
        allWeekDay.add("Tuesday");
        allWeekDay.add("Wednesday");
        allWeekDay.add("Thursday");
        allWeekDay.add("Friday");
        return InternshipOffer.builder()
                .jobName("stagiaire développement web")
                .description("connaissance en REACT")
                .startDate(new Date())
                .endDate(new Date())
                .weeklyWorkTime(37.5)
                .hourlySalary(19.67)
                .workDays(allWeekDay)
                .workShift(InternshipOffer.WorkShift.DAY)
                .workField(Department.COMPUTER_SCIENCE)
                .address("189, rue Mont-Goméry")
                .city("Montréal")
                .postalCode("JGH5E8")
                .monitor(getMonitorWithId())
                .build();
    }

    public static List<InternshipOffer> getListOfInternshipOffer() {
        InternshipOffer internshipOffer1 = getInternshipOfferWithId();
        InternshipOffer internshipOffer2 = getInternshipOfferWithId();

        List<InternshipOffer> internshipOffers = new ArrayList<>();
        internshipOffers.add(internshipOffer1);
        internshipOffers.add(internshipOffer2);
        return internshipOffers;
    }

    public static InternshipApplication getInternshipApplication() {
        return InternshipApplication.builder()
                .id("91448hkk58e00c02w02bjd4")
                .status(InternshipApplication.ApplicationStatus.WAITING)
                .internshipOffer(getInternshipOfferWithId())
                .student(getStudentWithId())
                .build();
    }

    public static List<InternshipApplication> getListOfInternshipApplication() {
        InternshipApplication internshipApplication1 = getInternshipApplication();
        InternshipApplication internshipApplication2 = getInternshipApplication();

        List<InternshipApplication> internshipApplications = new ArrayList<>();
        internshipApplications.add(internshipApplication1);
        internshipApplications.add(internshipApplication2);
        return internshipApplications;
    }

    public static PDFDocument getDocument() throws IOException {
        Path pdfPath = Paths.get(PDF_FILEPATH);
        return PDFDocument.builder()
                .name("documentTest.pdf")
                .content(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(pdfPath)))
                .build();
    }

    public static PDFDocument getEvaluationDocument(String documentName) throws IOException {
        Path pdfPath = Paths.get(PDF_EVALUATION_FILEPATH);
        return PDFDocument.builder()
                .name(documentName + DOCUMENT_EXTENSION)
                .content(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(pdfPath)))
                .build();
    }

    public static CV getCV() throws IOException {
        return CV.builder()
                .id("614662s17dfv9re02c85gt68dd5")
                .PDFDocument(getDocument())
                .build();
    }

    public static List<CV> getCVList() throws IOException {
        List<CV> cvList = new ArrayList<>();
        cvList.add(CV.builder()
                    .id("614662s17dfv9re02c85gt68dd5")
                    .PDFDocument(getDocument())
                    .build());
        cvList.add(CV.builder()
                .id("61eug62s17dfv9re02c85gt68dd5")
                .PDFDocument(getDocument())
                .build());
        return cvList;
    }

    public static Evaluation getEvaluation(String documentName) throws IOException {
        return Evaluation.builder()
                .document(getEvaluationDocument(documentName))
                .build();
    }

    public static Internship getInternship() throws IOException {
        return Internship.builder()
                .id("6141112s17d3gre02ce5gt68dq5")
                .internshipApplication(getInternshipApplication())
                .build();
    }
}
