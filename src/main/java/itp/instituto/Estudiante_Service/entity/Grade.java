package itp.instituto.Estudiante_Service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_grade")
public class Grade implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id" , unique = true ,length = 8, nullable = false, insertable = false, updatable = false)
    private String numberID;


    @Column(name="name", nullable=false)
    private String gradeName;
}
