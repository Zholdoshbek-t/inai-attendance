package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_class_time")
public class ClassTime {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer classHours;

    private Integer classMinutes;
}
