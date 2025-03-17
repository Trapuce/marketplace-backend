package com.trapuce.marketplace;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trapuce.marketplace.models.Attribute;
import com.trapuce.marketplace.models.AttributeType;
import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.repository.AttributeRepository;
import com.trapuce.marketplace.repository.CategoryRepository;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AttributeRepository attributeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<String> categoryNames = Arrays.asList(
				"Immobilier",
				"Véhicules",
				"Locations de vacances",
				"Emploi",
				"Mode",
				"Maison & Jardin",
				"Famille",
				"Électronique",
				"Loisirs",
				"Autres");

		for (String name : categoryNames) {

			Category category = new Category();
			category.setName(name);
			categoryRepository.save(category);

		}

		Category vehiculesCategory = this.categoryRepository.findByName("Véhicules")
				.orElseThrow(() -> new RuntimeException("Catégorie 'Véhicules' non trouvée"));

		List<Attribute> vehiculesAttributes = Arrays.asList(
				new Attribute("Marque", vehiculesCategory, AttributeType.TEXT, true, null),
				new Attribute("Modèle", vehiculesCategory, AttributeType.TEXT, true, null),
				new Attribute("Année", vehiculesCategory, AttributeType.NUMBER, true, null),
				new Attribute("Kilométrage", vehiculesCategory, AttributeType.NUMBER, true, null),
				new Attribute("Carburant", vehiculesCategory, AttributeType.SELECT, true, Map.of(
						"ESSENCE", "Essence",
						"DIESEL", "Diesel",
						"ELECTRIQUE", "Électrique")));
		Category electroniqueCategory = categoryRepository.findByName("Électronique")
				.orElseThrow(() -> new RuntimeException("Catégorie 'Électronique' non trouvée"));

		List<Attribute> electroniqueAttributes = Arrays.asList(
				new Attribute("Marque", electroniqueCategory, AttributeType.TEXT, true, null),
				new Attribute("Modèle", electroniqueCategory, AttributeType.TEXT, true, null),
				new Attribute("État", electroniqueCategory, AttributeType.SELECT, true, Map.of(
						"NEUF", "Neuf",
						"OCCASION", "Occasion",
						"REPARABLE", "Réparable")),
				new Attribute("Garantie", electroniqueCategory, AttributeType.BOOLEAN, false, null));

		for (Attribute attribute : electroniqueAttributes) {
			attributeRepository.save(attribute);

		}

		for (Attribute attribute : vehiculesAttributes) {
			attributeRepository.save(attribute);
		}

	}

}
