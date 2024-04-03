package kg.inai.qrgenerator.service.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import kg.inai.qrgenerator.commons.enums.ClassTime;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.commons.exception.ServerErrorException;
import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class QRService {

    private final UserRepository userRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final AttendanceRepository attendanceRepository;

    public BufferedImage generateQRCode(String classTime) {
        var teacher = getAuthentication();

        var subjectSchedule =
                subjectScheduleRepository.findByTeacherIdAndClassTimeIs(teacher.getId(), ClassTime.valueOf(classTime))
                        .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        var date = LocalDate.now();

        Attendance attendance;

        var optionalAttendance = attendanceRepository.findByDate(date);

        try {
            if (optionalAttendance.isEmpty()) {
                attendance = Attendance.builder()
                        .subjectSchedule(subjectSchedule)
                        .teacher(teacher)
                        .group(subjectSchedule.getGroup())
                        .date(date)
                        .build();

                attendanceRepository.save(attendance);
            } else {
                attendance = optionalAttendance.get();
            }

            var barcodeWriter = new QRCodeWriter();

            var bitMatrix = barcodeWriter.encode(String.valueOf(attendance.getId()),
                    BarcodeFormat.QR_CODE, 200, 200);

            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new ServerErrorException(INTERNAL_SERVER_ERROR);
        }
    }

    public User getAuthentication() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        return userRepository.findByUsername(authentication.getName())
//                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return userRepository.findByUsername("math.teacher")
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
