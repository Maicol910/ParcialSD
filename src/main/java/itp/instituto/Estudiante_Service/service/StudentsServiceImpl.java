package itp.instituto.Estudiante_Service.service;

import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.entity.Students;
import itp.instituto.Estudiante_Service.repository.StudentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class StudentsServiceImpl implements StudensService{
    @Autowired
    StudentsRepository studentsRepository;

    @Override
    public List<Students> findStudentsAll() {
        return studentsRepository.findAll();
    }

    @Override
    public List<Students> findStudentsByGrade(Grade grade) {
        return studentsRepository.findByGrade(grade);
    }

    @Override
    public Students createStudents(Students students) {
        Students studentDB = studentsRepository.findById(students.getId()).orElse(null);
        if (studentDB != null){
            return studentDB;
        }
        studentDB=studentsRepository.save(students);
        return studentDB;
    }

    /*
    @Override
    public Students createStudents(Students students) {
        Students studentsDB = studentsRepository.findByNumberID(students.getState());
        if (studentsDB != null) {
            return studentsDB;
        }

        students.setState("CREATED");
        studentsDB= studentsRepository.save(students);
        return studentsDB;
    }

     */

    @Override
    public Students updateStudents(Students students) {
        Students studentsDB = getStudents(students.getId());
        if (studentsDB == null){
            return null;
        }
        studentsDB.setFirstName(students.getFirstName());

        return studentsRepository.save(studentsDB);
    }

    @Override
    public Students deleteStudents(Students students) {
        Students studentsDB = getStudents(students.getId());
        if (studentsDB == null){
            return null;
        }
        //students.setState("DELETED");
        return studentsRepository.save(students);
    }

    @Override
    public Students getStudents(Long id) {
        return studentsRepository.findById(id).orElse(null);
    }
}
