package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_class_day")
public class ClassDay {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayEng;

    private String dayRus;

    private int dayOrder;
}
