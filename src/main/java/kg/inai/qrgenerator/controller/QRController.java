package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.service.inai.qr.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static kg.inai.qrgenerator.commons.constants.Endpoints.QR_URL;

@RestController
@RequestMapping(QR_URL)
@RequiredArgsConstructor
@Tag(name = "QR API", description = "API для генерации QR кода")
public class QRController {

    private final QRService qrService;

    @Operation(summary = "Генерация QR кода по айди пары")
    @GetMapping(value = "/{subjectScheduleId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generate(@PathVariable Long subjectScheduleId) {

        return ResponseEntity.ok(qrService.generateQRCode(subjectScheduleId));
    }
}
