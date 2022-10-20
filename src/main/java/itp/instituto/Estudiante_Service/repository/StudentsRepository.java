package itp.instituto.Estudiante_Service.repository;

import itp.instituto.Estudiante_Service.entity.Grade;
import itp.instituto.Estudiante_Service.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.naming.Name;
import java.util.List;

public interface StudentsRepository extends JpaRepository<Students,Long> {
    /*
    public Students findByNumberID(String numberID);
    public List<Students> findByLastName(String lastName);
*/
    public List<Students> findByGrade(Grade grade);

}

