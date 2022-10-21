package itp.instituto.Estudiante_Service.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.entity.Students;
import itp.instituto.Estudiante_Service.service.StudensService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/Students")

public class StudentsRest {

    @Autowired
    StudensService studensService;

    // -------------------Retrieve All Students-------------------------------------------
    @GetMapping
    public ResponseEntity<List<Students>> ListAllStudents(@RequestParam(name = "GradeId", required = false) Long gradeId){
        log.info("Listado de estudiantes por grado", gradeId);
        List<Students> students = new ArrayList<>();
        if (null== gradeId){
            students=studensService.findStudentsAll();
            if (students.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            Grade grade=new Grade();
            grade.setId(gradeId);
            students = studensService.findStudentsByGrade(grade);
            if (null == students){
                log.error("Students with Grade id {} not found.", gradeId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(students);
    }

    // -------------------Retrieve Single Customer-------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Students> getCustomer(@PathVariable("id")long id){
        log.info("Fetching Students with id {}", id);
        Students students = studensService.getStudents(id);
        if (null == students){
            log.error("Students with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    // -------------------Create a Students-------------------------------------------

    @PostMapping
    public ResponseEntity<Students> createStudents (@RequestBody Students students, BindingResult result){
        log.info("Creating Students : {}", students);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Students studentsDB = studensService.createStudents(students);

        return  ResponseEntity.status(HttpStatus.CREATED).body(studentsDB);

    }

    // ------------------- Update a Customer ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateStudents(@PathVariable("id") long id, @RequestBody Students students){
        log.info("Updating Students with id {}", id);
        Students currentStudents = studensService.getStudents(id);

        if (null == currentStudents){
            log.error("Unable to update. Students with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        students.setId(id);
        currentStudents=studensService.updateStudents(students);
        return ResponseEntity.ok(currentStudents);
    }

    // ------------------- Delete a Customer-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Students> deleteStudents (@PathVariable("id")long id){
        log.info("Fetching & Deleting Students with id {}",id);

        Students students = studensService.getStudents(id);
        boolean deleted = studensService.deleteStudents(students);

        if (!deleted){
            log.error("Unable to delete. Students with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }


}

