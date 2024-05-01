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
import static kg.inai.qrgenerator.commons.enums.Role.*;

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

        if(!userRepository.existsByUsername("admin")) {
            var admin = User.builder()
                    .firstName("ADMIN")
                    .lastName("ADMIN")
                    .middleName("ADMIN")
                    .username("admin")
                    .password("admin")
                    .valid(true)
                    .role(ADMIN)
                    .build();

            userRepository.save(admin);
        }

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

        var classDays = List.of(monday, tuesday, wednesday, thursday, friday, saturday);

        for(ClassDay classDay: classDays) {
            if(!classDayRepository.existsByDayEng(classDay.getDayEng())) {
                classDayRepository.save(classDay);
            }
        }

        var firstClass = ClassTime.builder().classHours(8).classMinutes(0).build();
        var secondClass = ClassTime.builder().classHours(9).classMinutes(30).build();
        var thirdClass = ClassTime.builder().classHours(11).classMinutes(0).build();
        var fourthClass = ClassTime.builder().classHours(12).classMinutes(30).build();
        var fifthClass = ClassTime.builder().classHours(14).classMinutes(0).build();
        var sixthClass = ClassTime.builder().classHours(15).classMinutes(30).build();
        var seventhClass = ClassTime.builder().classHours(17).classMinutes(0).build();
        var eighthClass = ClassTime.builder().classHours(18).classMinutes(30).build();
        var ninthClass = ClassTime.builder().classHours(20).classMinutes(0).build();

        var classTimes = List.of(firstClass, secondClass, thirdClass, fourthClass, fifthClass,
                sixthClass, seventhClass, eighthClass, ninthClass);

        for(ClassTime classTime: classTimes) {
            if(!classTimeRepository.existsByClassHoursAndClassMinutes(classTime.getClassHours(),
                    classTime.getClassMinutes())) {
                classTimeRepository.save(classTime);
            }
        }
    }
}
