package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import kg.inai.qrgenerator.commons.enums.ClassDay;
import kg.inai.qrgenerator.commons.enums.ClassTime;
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

    @Enumerated(EnumType.ORDINAL)
    private ClassTime classTime;

    @Enumerated(EnumType.ORDINAL)
    private ClassDay dayOfWeek;

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
