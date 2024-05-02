package kg.inai.qrgenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class QrGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrGeneratorApplication.class, args);
	}
}
