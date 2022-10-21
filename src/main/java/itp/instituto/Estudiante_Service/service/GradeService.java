package itp.instituto.Estudiante_Service.service;

import itp.instituto.Estudiante_Service.entity.Grade;

import java.util.List;

public interface GradeService {
    public List<Grade> findGradeAll();

    public Grade createGrade(Grade grade);
    public Grade updateGrade(Grade grade);
    public boolean deleteGrade(Grade grade);
    public  Grade getGrade(Long id);
}
