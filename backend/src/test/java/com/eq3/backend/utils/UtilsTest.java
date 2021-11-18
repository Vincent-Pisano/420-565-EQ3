package com.eq3.backend.utils;

import com.eq3.backend.model.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.eq3.backend.utils.Utils.getNextSessionFromDate;

public class UtilsTest {

    public final static String DOCUMENT_EXTENSION = "Evaluation.pdf";
    public final static String DOCUMENT_NAME = "student";

    public final static String SESSION = getNextSessionFromDate(new Date());

    public final static String PDF_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\documentTest.pdf";
    public final static String IMAGE_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\image.png";

    public final static String PDF_EVALUATION_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\studentEvaluation.pdf";

    public static Student getStudentWithIdAndSession(){
        Student student = getStudentWithoutId();
        student.setId("61478hgk58e00c02c02bhd5");
        student.setSessions(Collections.singletonList(SESSION));
        return student;
    }

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

    public static List<Student> getListOfStudentsWithSessions(){
        Student student1 = getStudentWithId();
        student1.setSessions(Collections.singletonList(SESSION));
        Student student2 = getStudentWithId();
        student2.setSessions(Collections.singletonList(SESSION));
        List<Student> studentsList = new ArrayList();
        studentsList.add(student1);
        studentsList.add(student2);
        return studentsList;
    }

    public static List<Student> getListOfStudentsWithoutStudentEvaluation(){
        Student student1 = getStudentWithId();
        List<Student> studentsList = new ArrayList();
        studentsList.add(student1);
        return studentsList;
    }

    public static List<Student> getListOfStudentsWithoutEnterpriseEvaluation(){
        return getListOfStudentsWithoutStudentEvaluation();
    }

    public static Monitor getMonitorWithId(){
        Monitor monitor = getMonitorWithoutId();
        monitor.setId("6164accb3418286e9469714a");
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
        supervisor.setId("618d46c6ced7a6434b8a0e93");
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
                .id("91448hkk58e00c02w02bjd4")
                .jobName("stagiaire developpement web")
                .description("connaissance en REACT")
                .startDate(new Date())
                .endDate(new Date())
                .weeklyWorkTime(37.5)
                .hourlySalary(19.67)
                .workDays(allWeekDay)
                .workShift(InternshipOffer.WorkShift.DAY)
                .workField(Department.COMPUTER_SCIENCE)
                .address("189, rue Mont-Gomery")
                .city("Montreal")
                .postalCode("JGH5E8")
                .session(getSession(new Date()))
                .monitor(getMonitorWithId())
                .build();
    }

    public static String getSession(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return month <= 5 ? year + " Hiver" : year + " Ete";
    }

    public static String getSession() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return month <= 5 ? year + " Hiver" : year + " Été";
    }

    public static List<String> getSessionList() {
        return Collections.singletonList(getSession(getInternshipOfferWithId().getStartDate()));
    }

    public static List<String> getListOfSessions(){
        List<String> listSessions = new ArrayList<>();
        listSessions.add(getSession(new Date()));
        return listSessions;
    }

    public static List<InternshipOffer> getListOfInternshipOffer() {
        InternshipOffer internshipOffer1 = getInternshipOfferWithId();
        InternshipOffer internshipOffer2 = getInternshipOfferWithId();

        List<InternshipOffer> internshipOffers = new ArrayList<>();
        internshipOffers.add(internshipOffer1);
        internshipOffers.add(internshipOffer2);
        return internshipOffers;
    }

    public static List<InternshipOffer> getListOfInternshipOfferWithDifferentSession() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        InternshipOffer internshipOffer1 = getInternshipOfferWithId();
        c.add(Calendar.MONTH, 6);
        internshipOffer1.setSession(getSession(c.getTime()));

        InternshipOffer internshipOffer2 = getInternshipOfferWithId();
        c.add(Calendar.MONTH, 6);
        internshipOffer2.setSession(getSession(c.getTime()));

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

    public static List<InternshipApplication> getListOfInternshipApplicationWithInterviewDate() throws ParseException {
        InternshipApplication internshipApplication1 = getInternshipApplication();
        InternshipApplication internshipApplication2 = getInternshipApplication();

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/2021");
        internshipApplication1.setInterviewDate(date);
        internshipApplication2.setInterviewDate(date);

        List<InternshipApplication> internshipApplications = new ArrayList<>();
        internshipApplications.add(internshipApplication1);
        internshipApplications.add(internshipApplication2);
        return internshipApplications;
    }

    public static List<InternshipApplication> getListOfCompletedInternshipApplication() {
        InternshipApplication internshipApplication1 = getInternshipApplication();
        InternshipApplication internshipApplication2 = getInternshipApplication();
        internshipApplication1.setStatus(InternshipApplication.ApplicationStatus.COMPLETED);
        internshipApplication2.setStatus(InternshipApplication.ApplicationStatus.COMPLETED);
        Student student = getStudentWithId();
        student.setId("61478hgk58e00c02c02bwj1");
        internshipApplication2.setStudent(student);


        List<InternshipApplication> internshipApplications = new ArrayList<>();
        internshipApplications.add(internshipApplication1);
        internshipApplications.add(internshipApplication2);
        return internshipApplications;
    }
    public static List<InternshipApplication> getListOfAcceptedInternshipApplication() {
        InternshipApplication internshipApplication1 = getInternshipApplication();
        InternshipApplication internshipApplication2 = getInternshipApplication();
        internshipApplication1.setStatus(InternshipApplication.ApplicationStatus.ACCEPTED);
        internshipApplication2.setStatus(InternshipApplication.ApplicationStatus.ACCEPTED);
        Student student = getStudentWithId();
        student.setId("61478hgk58e00c02c02bwj1");
        internshipApplication2.setStudent(student);


        List<InternshipApplication> internshipApplications = new ArrayList<>();
        internshipApplications.add(internshipApplication1);
        internshipApplications.add(internshipApplication2);
        return internshipApplications;
    }

    public static List<InternshipApplication> getListOfInternshipApplicationWithDifferentStudent() {
        List<InternshipApplication> internshipApplications = getListOfInternshipApplication();
        InternshipApplication internshipApplication = internshipApplications.get(0);
        Student student = internshipApplication.getStudent();
        student.setId("srg2sr1g681q35g1q6g1q");
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
                .id("6141112s17d3eye02ce5gt68dq5")
                .internshipApplication(getInternshipApplication())
                .build();
    }

    public static List<Internship> getInternshipList() throws IOException {
        Internship internship1 = getInternship();
        Internship internship2 = getInternship();

        List<Internship> internships = new ArrayList<>();
        internships.add(internship1);
        internships.add(internship2);
        return internships;
    }

    public static List<Internship> getInternshipListCompleted() throws IOException {
        List<Internship> internships = new ArrayList<>();
        internships.add(getInternship());
        internships.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            internshipApplication.setStatus(InternshipApplication.ApplicationStatus.COMPLETED);
        });
        return internships;
    }

    public static Internship getInternshipWithInternshipContract() throws IOException {
        return Internship.builder()
                .id("6141112s17d3gre02ce5gt68dq5")
                .internshipApplication(getInternshipApplication())
                .internshipContract(getDocument())
                .build();
    }

    public static Binary getImage() throws IOException {
        Path imagePDF = Paths.get(IMAGE_FILEPATH);
        return new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(imagePDF));
    }
}
