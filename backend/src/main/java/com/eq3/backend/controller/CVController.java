package com.eq3.backend.controller;

import com.eq3.backend.model.Student;
import com.eq3.backend.service.CVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.eq3.backend.utils.UtilsController.CVControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;
import static com.eq3.backend.utils.UtilsController.APPLICATION_JSON_AND_CHARSET_UTF8;
import static com.eq3.backend.utils.UtilsController.MULTI_PART_FROM_DATA;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_DOCUMENT;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class CVController {

    private final CVService service;

    public CVController(CVService service) {
        this.service = service;
    }

    @PostMapping(value = URL_SAVE_CV,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
    public ResponseEntity<Student> saveCV(@RequestPart(name = REQUEST_PART_DOCUMENT) MultipartFile multipartFile,
                                          @PathVariable String id) {
        return service.saveCV(id, multipartFile)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @DeleteMapping(URL_DELETE_CV)
    public ResponseEntity<Student> deleteCV(@PathVariable String idStudent, @PathVariable String idCV) {
        return service.deleteCV(idStudent, idCV)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_UPDATE_ACTIVE_CV)
    public ResponseEntity<Student> updateActiveCV(@PathVariable String idStudent, @PathVariable String idCV) {
        return service.updateActiveCV(idStudent, idCV)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_CV_ACTIVE_NOT_VALID)
    public ResponseEntity<List<Student>> getAllStudentsWithActiveAndNotValidCV(@PathVariable String session) {
        return service.getAllStudentWithCVActiveWaitingValidation(session)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_VALIDATE_CV)
    public ResponseEntity<Student> validateCVOfStudent(@PathVariable String idStudent) {
        return service.validateCVOfStudent(idStudent)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
