/*package com.pravdin.democassandra.controller;

//import com.pravdin.democassandra.model.SimpleTable;
import com.pravdin.democassandra.repositories.TestiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
*/
/*@RestController
@RequestMapping("/cassandra")
public class CassandraController {

    @Autowired
    private TestiRepository testiRepository;

    @PostMapping
    public ResponseEntity<SimpleTable> saveIntoSimpleTable(@RequestBody SimpleTable simpleTable){
        SimpleTable savedData = testiRepository.save(simpleTable);
        return new ResponseEntity<>(savedData, HttpStatus.OK);
    }

}*/
