package itp.instituto.Estudiante_Service.repository;


 import itp.instituto.Estudiante_Service.entity.Grade;
 import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.List;
public interface GradeRepository extends JpaRepository<Grade, Long> {
    public Grade findByNumberID(String numberID);
    public List<Grade> findByGradeName(String RegionName);
}
