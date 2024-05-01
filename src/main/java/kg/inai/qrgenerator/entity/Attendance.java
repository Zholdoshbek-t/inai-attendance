package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_attendance")
public class Attendance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private User teacher;

    @ManyToOne
    @JoinColumn(
            name = "subject_schedule_id",
            referencedColumnName = "id"
    )
    private SubjectSchedule subjectSchedule;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    private Group group;

    @ManyToMany
    @JoinTable(
            name = "tb_student_attendance",
            joinColumns = @JoinColumn(name = "attendance_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;
}
