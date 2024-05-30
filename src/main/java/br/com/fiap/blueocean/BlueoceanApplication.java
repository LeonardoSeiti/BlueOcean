package br.com.fiap.blueocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@Controller
@OpenAPIDefinition(
		info = @Info(
				title = "Blue Ocean API",
				version = "1.0",
				description = "Projeto para Global solution, nosso projeto visa monitorar a poluição marinha e fornecer respostas rápidas e eficientes em tempo real. Utilizando drones para garantir mobilidade e praticidade, buscamos aprimorar a detecção e resposta a incidentes de poluição, reduzindo significativamente o tempo de reação a esses problemas."
		)

)
public class BlueoceanApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueoceanApplication.class, args);
	}

		@RequestMapping
		@ResponseBody
		public String home() {
			return "Hello, esse é o projeto Blue Ocean!";
		}
}
