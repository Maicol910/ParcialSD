package itp.instituto.Estudiante_Service.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.service.GradeService;
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
@RequestMapping("/Grade")
public class GradeRest {
    @Autowired
    GradeService gradeService;

    // -------------------Retrieve All Grades--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Grade>> listAllGrade(@RequestParam(name = "gradeId" , required = false) Long gradeId ) {
        List<Grade> grades =  new ArrayList<>();
        if (null ==  gradeId) {
            grades = gradeService.findGradeAll();
            if (grades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return  ResponseEntity.ok(grades);
        /*
        else{
            Grade Grade= new Grade();
            Grade.setId(gradeId);
            grades = gradeService.findGradesById(Grade);
            if ( null == grades ) {
                log.error("Grades with Grade id {} not found.", gradeId);
                return  ResponseEntity.notFound().build();
            }
        }
         */
    }

    // -------------------Retrieve Single Grade------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Grade> getGrade(@PathVariable("id") long id) {
        log.info("Fetching Grade with id {}", id);
        Grade grade = gradeService.getGrade(id);
        if (  null == grade) {
            log.error("Grade with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(grade);
    }

    // -------------------Create a Grade-------------------------------------------

    @PostMapping
    public ResponseEntity<Grade> createGrade( @RequestBody Grade grade, BindingResult result) {
        log.info("Creating Grade : {}", grade);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Grade gradeDB = gradeService.createGrade (grade);

        return  ResponseEntity.status( HttpStatus.CREATED).body(gradeDB);
    }

    // ------------------- Update a Grade ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable("id") long id, @RequestBody Grade grade) {
        log.info("Updating Grade with id {}", id);

        Grade currentGrade = gradeService.getGrade(id);

        if ( null == currentGrade ) {
            log.error("Unable to update. Grade with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        grade.setId(id);
        currentGrade=gradeService.updateGrade(grade);
        return  ResponseEntity.ok(currentGrade);
    }

    // ------------------- Delete a Grade-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Grade with id {}", id);

        Grade grade = gradeService.getGrade(id);
        boolean deleted = gradeService.deleteGrade(grade);
        if ( !deleted ) {
            log.error("Unable to delete. Grade with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok().build();
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
