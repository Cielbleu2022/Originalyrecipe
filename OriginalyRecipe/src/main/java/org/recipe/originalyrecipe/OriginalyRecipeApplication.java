package org.recipe.originalyrecipe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title= "Originaly Recipe",
				version = "1.0.0",
				description = "It's an application for all the food lover " +
						"and wanna try to learn  originally recipe. Hope you enjoy it miammmm c'est bon",

				termsOfService="to watch and learn recipes",
				contact = @Contact(
						name = "Moustafa",
						email = "finonty21@yahoo.fr"
				),
				license = @License(
						name = "Licence",
						url = " "

				)
		)
)
public class OriginalyRecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OriginalyRecipeApplication.class, args);
	}

}
