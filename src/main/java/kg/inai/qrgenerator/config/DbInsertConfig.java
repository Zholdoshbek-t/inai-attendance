package kg.inai.qrgenerator.config;

import jakarta.annotation.PostConstruct;
import kg.inai.qrgenerator.entity.*;
import kg.inai.qrgenerator.entity.repository.*;
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
    private final ClassDayRepository classDayRepository;
    private final ClassTimeRepository classTimeRepository;

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @PostConstruct
    public void init() {
        var monday = ClassDay.builder()
                .dayEng("MONDAY")
                .dayRus("Понедельник")
                .dayOrder(1)
                .build();

        var tuesday = ClassDay.builder()
                .dayEng("TUESDAY")
                .dayRus("Вторник")
                .dayOrder(1)
                .build();

        var wednesday = ClassDay.builder()
                .dayEng("WEDNESDAY")
                .dayRus("Среда")
                .dayOrder(1)
                .build();

        var thursday = ClassDay.builder()
                .dayEng("THURSDAY")
                .dayRus("Четверг")
                .dayOrder(1)
                .build();

        var friday = ClassDay.builder()
                .dayEng("FRIDAY")
                .dayRus("Пятница")
                .dayOrder(1)
                .build();

        var saturday = ClassDay.builder()
                .dayEng("SATURDAY")
                .dayRus("Суббота")
                .dayOrder(1)
                .build();

        classDayRepository.saveAll(List.of(monday, tuesday, wednesday, thursday, friday, saturday));

        var firstClass = ClassTime.builder().classHours(8).classMinutes(0).build();
        var secondClass = ClassTime.builder().classHours(9).classMinutes(30).build();
        var thirdClass = ClassTime.builder().classHours(11).classMinutes(0).build();
        var fourthClass = ClassTime.builder().classHours(12).classMinutes(30).build();
        var fifthClass = ClassTime.builder().classHours(14).classMinutes(0).build();
        var sixthClass = ClassTime.builder().classHours(15).classMinutes(30).build();
        var seventhClass = ClassTime.builder().classHours(17).classMinutes(0).build();
        var eighthClass = ClassTime.builder().classHours(18).classMinutes(30).build();
        var ninthClass = ClassTime.builder().classHours(20).classMinutes(0).build();

        classTimeRepository.saveAll(List.of(
                firstClass, secondClass, thirdClass, fourthClass, fifthClass,
                sixthClass, seventhClass, eighthClass, ninthClass
        ));

        var groupAin = Group.builder().name("АИН-1-20").build();
        var groupWin = Group.builder().name("ВИН-1-20").build();

        groupRepository.saveAll(List.of(groupAin, groupWin));

        var teacherMath = User.builder()
                .firstName("Айбек")
                .lastName("Айбек")
                .middleName("Айбек")
                    .username("math.teacher")
                .password("123456")
                .valid(true)
                .role(TEACHER)
                .build();

        var teacherDb = User.builder()
                .firstName("Салтанат")
                .lastName("Салтанат")
                .middleName("Салтанат")
                .username("db.teacher")
                .password("123456")
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
                    .password("123456")
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
                    .password("123456")
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

//        var mathOneAin = SubjectSchedule.builder()
//                .group(groupAin)
//                .subject(math)
//                .teacher(teacherMath)
//                .dayOfWeek(MONDAY)
//                .classTime(FIRST)
//                .build();
//
//        var mathTwoAin = SubjectSchedule.builder()
//                .group(groupAin)
//                .subject(math)
//                .teacher(teacherMath)
//                .dayOfWeek(WEDNESDAY)
//                .classTime(FIRST)
//                .build();
//
//        var dbOneAin = SubjectSchedule.builder()
//                .group(groupAin)
//                .subject(db)
//                .teacher(teacherDb)
//                .dayOfWeek(TUESDAY)
//                .classTime(FIRST)
//                .build();
//
//        var dbTwoAin = SubjectSchedule.builder()
//                .group(groupAin)
//                .subject(db)
//                .teacher(teacherDb)
//                .dayOfWeek(THURSDAY)
//                .classTime(FIRST)
//                .build();
//
//        var mathOneWin = SubjectSchedule.builder()
//                .group(groupWin)
//                .subject(math)
//                .teacher(teacherMath)
//                .dayOfWeek(MONDAY)
//                .classTime(SECOND)
//                .build();
//
//        var mathTwoWin = SubjectSchedule.builder()
//                .group(groupWin)
//                .subject(math)
//                .teacher(teacherMath)
//                .dayOfWeek(WEDNESDAY)
//                .classTime(SECOND)
//                .build();
//
//        var dbOneWin = SubjectSchedule.builder()
//                .group(groupWin)
//                .subject(db)
//                .teacher(teacherDb)
//                .dayOfWeek(TUESDAY)
//                .classTime(SECOND)
//                .build();
//
//        var dbTwoWin = SubjectSchedule.builder()
//                .group(groupWin)
//                .subject(db)
//                .teacher(teacherDb)
//                .dayOfWeek(THURSDAY)
//                .classTime(SECOND)
//                .build();
//
//        subjectScheduleRepository.saveAll(List.of(
//                mathOneAin, mathTwoAin, dbOneAin, dbTwoAin,
//                mathOneWin, mathTwoWin, dbOneWin, dbTwoWin));
    }
}
