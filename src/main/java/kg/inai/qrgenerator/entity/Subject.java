package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_subject")
public class Subject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int year;

    private int semester;
}
