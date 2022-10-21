package itp.instituto.Estudiante_Service.service;

import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.repository.GradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GradeServiceImpl implements GradeService{
    @Autowired
    GradeRepository gradeRepository;

    @Override
    public List<Grade> findGradeAll() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade createGrade(Grade grade) {
        Grade gradeDB = gradeRepository.findById(grade.getId()).orElse(null);
        if (gradeDB != null){
            return gradeDB;
        }
        gradeDB = gradeRepository.save(grade);
        return gradeDB;
    }

    /*
    @Override
    public Customer createCustomer(Customer customer) {

        Customer customerDB = customerRepository.findByNumberID ( customer.getNumberID () );
        if (customerDB != null){
            return  customerDB;
        }

        customer.setState("CREATED");
        customerDB = customerRepository.save ( customer );
        return customerDB;
    }

     */

    @Override
    public Grade updateGrade(Grade grade) {
        Grade gradeDB = getGrade(grade.getId());
        if (gradeDB == null){
            return  null;
        }
        gradeDB.setGradeName(grade.getGradeName());

        return  gradeRepository.save(gradeDB);
    }

    @Override
    public boolean deleteGrade(Grade grade) {
        if (grade == null){
            return  false;
        }
        //grade.setState("DELETED");
        gradeRepository.delete(grade);
        return true;
    }

    @Override
    public Grade getGrade(Long id) {
        return  gradeRepository.findById(id).orElse(null);
    }
}
