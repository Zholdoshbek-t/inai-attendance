package kg.inai.qrgenerator.config;

import jakarta.annotation.PostConstruct;
import kg.inai.qrgenerator.entity.Group;
import kg.inai.qrgenerator.entity.Subject;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.SubjectRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static kg.inai.qrgenerator.commons.enums.ClassDay.*;
import static kg.inai.qrgenerator.commons.enums.ClassTime.FIRST;
import static kg.inai.qrgenerator.commons.enums.ClassTime.SECOND;
import static kg.inai.qrgenerator.commons.enums.Role.STUDENT;
import static kg.inai.qrgenerator.commons.enums.Role.TEACHER;

@Configuration
@RequiredArgsConstructor
public class DbInsertConfig {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @PostConstruct
    public void init() {
        var groupAin = Group.builder().name("АИН-1-20").build();
        var groupWin = Group.builder().name("ВИН-1-20").build();

        groupRepository.saveAll(List.of(groupAin, groupWin));

        var teacherMath = User.builder()
                .firstName("Айбек")
                .lastName("Айбек")
                .middleName("Айбек")
                    .username("math.teacher")
                .password(passwordEncoder.encode("123456"))
                .valid(true)
                .role(TEACHER)
                .build();

        var teacherDb = User.builder()
                .firstName("Салтанат")
                .lastName("Салтанат")
                .middleName("Салтанат")
                .username("db.teacher")
                .password(passwordEncoder.encode("123456"))
                .valid(true)
                .role(TEACHER)
                .build();

        List<User> users = new ArrayList<>(List.of(teacherMath, teacherDb));

        for(int i = 0; i < 10; i++) {
            var studentAin = User.builder()
                    .firstName("Азамат")
                    .lastName("Азамат")
                    .middleName("Азамат")
                    .username("student.ain" + i)
                .password(passwordEncoder.encode("123456"))
                    .valid(true)
                    .role(STUDENT)
                    .build();

            users.add(studentAin);
        }

        for(int i = 0; i < 10; i++) {
            var studentWin = User.builder()
                    .firstName("Байэл")
                    .lastName("Байэл")
                    .middleName("Байэл")
                    .username("student.win" + i)
                .password(passwordEncoder.encode("123456"))
                    .valid(true)
                    .role(STUDENT)
                    .build();

            users.add(studentWin);
        }

        userRepository.saveAll(users);

        var math = Subject.builder()
                .name("ЯП 1")
                .year(2024)
                .semester(1)
                .build();

        var db = Subject.builder()
                .name("Базы данных 1")
                .year(2024)
                .semester(1)
                .build();

        subjectRepository.saveAll(List.of(math, db));

        var mathOneAin = SubjectSchedule.builder()
                .group(groupAin)
                .subject(math)
                .teacher(teacherMath)
                .dayOfWeek(MONDAY)
                .classTime(FIRST)
                .build();

        var mathTwoAin = SubjectSchedule.builder()
                .group(groupAin)
                .subject(math)
                .teacher(teacherMath)
                .dayOfWeek(WEDNESDAY)
                .classTime(FIRST)
                .build();

        var dbOneAin = SubjectSchedule.builder()
                .group(groupAin)
                .subject(db)
                .teacher(teacherDb)
                .dayOfWeek(TUESDAY)
                .classTime(FIRST)
                .build();

        var dbTwoAin = SubjectSchedule.builder()
                .group(groupAin)
                .subject(db)
                .teacher(teacherDb)
                .dayOfWeek(THURSDAY)
                .classTime(FIRST)
                .build();

        var mathOneWin = SubjectSchedule.builder()
                .group(groupWin)
                .subject(math)
                .teacher(teacherMath)
                .dayOfWeek(MONDAY)
                .classTime(SECOND)
                .build();

        var mathTwoWin = SubjectSchedule.builder()
                .group(groupWin)
                .subject(math)
                .teacher(teacherMath)
                .dayOfWeek(WEDNESDAY)
                .classTime(SECOND)
                .build();

        var dbOneWin = SubjectSchedule.builder()
                .group(groupWin)
                .subject(db)
                .teacher(teacherDb)
                .dayOfWeek(TUESDAY)
                .classTime(SECOND)
                .build();

        var dbTwoWin = SubjectSchedule.builder()
                .group(groupWin)
                .subject(db)
                .teacher(teacherDb)
                .dayOfWeek(THURSDAY)
                .classTime(SECOND)
                .build();

        subjectScheduleRepository.saveAll(List.of(
                mathOneAin, mathTwoAin, dbOneAin, dbTwoAin,
                mathOneWin, mathTwoWin, dbOneWin, dbTwoWin));
    }
}
