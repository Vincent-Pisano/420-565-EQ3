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
    public final static String DOCUMENT_NAME = "documentTest";

    public final static String PDF_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\ressources\\assets\\documentTest.pdf";

    public static Student getStudent(){
        return Student.builder()
                .id("61478hgk58e00c02c02bhd5")
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
    }

    public static List<Student> getListOfStudents(){
        Student student1 = Student.builder()
                .id("6144343k58e00c02c02bhd5")
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        Student student2 = Student.builder()
                .id("61478hgk58e00c02c02bhd2432")
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        List<Student> studentsList = new ArrayList();
        studentsList.add(student1);
        studentsList.add(student2);
        return studentsList;
    }

    public static Monitor getMonitor(){
        return Monitor.builder()
                .id("61478hgk580000jbhd5")
                .username("M1234313")
                .password("DAJo90l")
                .email("Nicolas.lavoie43@gmail.com")
                .firstName("Nicolas")
                .lastName("Lavoie")
                .jobTitle("Sales manager")
                .enterpriseName("Amazon")
                .build();
    }

    public static Supervisor getSupervisor(){
        return Supervisor.builder()
                .id("15848hgk58e00c02c02bhd5")
                .username("S1298896")
                .password("JeA55E!")
                .email("jeanne.dumond@gmail.com")
                .firstName("Jeanne")
                .lastName("Dumond")
                .department(Department.NURSING)
                .build();
    }

    public static List<Supervisor> getListOfSupervisors(){
        Supervisor monitor1 = Supervisor.builder()
                .id("6144343k58e00c02c02bhd5")
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        Supervisor monitor2 = Supervisor.builder()
                .id("61478hgk58e00c02c02bhd2432")
                .username("E1257896")
                .password("DAJo90l")
                .email("daniel.jolicoeur5@gmail.com")
                .firstName("Daniel")
                .lastName("Jolicoeur")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        List<Supervisor> supervisorsList = new ArrayList();
        supervisorsList.add(monitor1);
        supervisorsList.add(monitor2);
        return supervisorsList;
    }

    public static InternshipManager getInternshipManager(){
        return InternshipManager.builder()
                .id("6146tf5eg8e00c02c02bhd5")
                .username("G42415")
                .password("qWeRtY987")
                .email("marcel.tremblay@outlook.com")
                .firstName("Marcel")
                .lastName("Tremblay")
                .build();
    }

    public static InternshipOffer getInternshipOffer() {
        List<String> allWeekDay = new ArrayList<>();
        allWeekDay.add("Monday");
        allWeekDay.add("Tuesday");
        allWeekDay.add("Wednesday");
        allWeekDay.add("Thursday");
        allWeekDay.add("Friday");
        return InternshipOffer.builder()
                .id("91448hkk58e00c02w02bjd4")
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
                .build();
    }

    public static List<InternshipOffer> getListOfInternshipOffer() {
        List<String> allWeekDay = new ArrayList<>();
        allWeekDay.add("Monday");
        allWeekDay.add("Tuesday");
        allWeekDay.add("Wednesday");
        allWeekDay.add("Thursday");
        allWeekDay.add("Friday");
        InternshipOffer internshipOffer1 = InternshipOffer.builder()
                .id("91448hkk58e00c02w02bjd4")
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
                .monitor(getMonitor())
                .build();
        InternshipOffer internshipOffer2 = InternshipOffer.builder()
                .id("51228hgg58e11c12w02bjd3")
                .jobName("Stagiaire Technicien")
                .description("Connaissance domaine technique")
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
                .monitor(getMonitor())
                .build();
        List<InternshipOffer> internshipOffers = new ArrayList<>();
        internshipOffers.add(internshipOffer1);
        internshipOffers.add(internshipOffer2);
        return internshipOffers;
    }

    public static PDFDocument getDocument() throws IOException {
        Path pdfPath = Paths.get(PDF_FILEPATH);
        return PDFDocument.builder()
                .name("documentTest.pdf")
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

    public static Evaluation getEvaluation() throws IOException {
        return Evaluation.builder()
                .document(getDocument())
                .build();
    }

    public static InternshipApplication getInternshipApplication() {
        return InternshipApplication.builder()
                .id("91448hkk58e00c02w02bjd4")
                .status(InternshipApplicationStatus.WAITING)
                .internshipOffer(getInternshipOffer())
                .build();
    }

}
