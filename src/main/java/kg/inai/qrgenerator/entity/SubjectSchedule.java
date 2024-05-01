package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_subject_schedule")
public class SubjectSchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "class_time",
            referencedColumnName = "id"
    )
    private ClassTime classTime;

    @ManyToOne
    @JoinColumn(
            name = "class_day",
            referencedColumnName = "id"
    )
    private ClassDay classDay;

    @ManyToOne
    @JoinColumn(
            name = "subject_id",
            referencedColumnName = "id"
    )
    private Subject subject;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    private Group group;

    @ManyToOne
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private User teacher;
}
