package kg.inai.qrgenerator.service.inai.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.commons.exception.ServerErrorException;
import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class QRService {

    private final SubjectScheduleRepository subjectScheduleRepository;
    private final AttendanceRepository attendanceRepository;

    public BufferedImage generateQRCode(Long subjectScheduleId) {

        var subjectSchedule = subjectScheduleRepository.findById(subjectScheduleId)
                .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        var date = LocalDate.now();

        Attendance attendance;

        var optionalAttendance = attendanceRepository.findByDateAndSubjectSchedule(date, subjectSchedule);

        try {
            if (optionalAttendance.isEmpty()) {
                attendance = Attendance.builder()
                        .subjectSchedule(subjectSchedule)
                        .teacher(subjectSchedule.getTeacher())
                        .group(subjectSchedule.getGroup())
                        .date(date)
                        .students(new HashSet<>())
                        .time(LocalTime.now()) // 10 sek attendance
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
}
