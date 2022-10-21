package itp.instituto.Estudiante_Service.service;

import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.entity.Students;

import java.util.List;
public interface StudensService {

    public List<Students> findStudentsAll();
    public List<Students> findStudentsByGrade(Grade grade);

    public Students createStudents(Students students);
    public Students updateStudents(Students students);
    public boolean deleteStudents(Students students);
    public Students getStudents(Long id);
}
