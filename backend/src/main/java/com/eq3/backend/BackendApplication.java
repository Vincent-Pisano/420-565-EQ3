package com.eq3.backend;

import com.eq3.backend.model.Student;
import com.eq3.backend.repository.StudentRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    public Student addCV(MultipartFile file) throws IOException {
        Student student = studentRepository.findById("614c7cae6c77f372e0970243").get();
        Binary newCV = new Binary(BsonBinarySubType.BINARY, file.getBytes());
        List<Binary> cvList = student.getCvList();
        cvList.add(newCV);
        student.setCvList(cvList);
        return studentRepository.save(student);
    }

}
