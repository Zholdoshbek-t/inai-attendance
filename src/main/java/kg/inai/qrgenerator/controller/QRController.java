package kg.inai.qrgenerator.controller;

import kg.inai.qrgenerator.service.utils.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.image.BufferedImage;

@Controller
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;

    @GetMapping(value = "/{classTime}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generate(@PathVariable String classTime) {
        return ResponseEntity.ok(qrService.generateQRCode(classTime));
    }
}
