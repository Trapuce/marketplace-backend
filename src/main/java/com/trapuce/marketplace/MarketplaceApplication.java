package com.trapuce.marketplace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.repository.CategoryRepository;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner{


	@Autowired
	public  CategoryRepository categoryRepository  ;
	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		  // Root categories
            Category immobilier = new Category(null, "Immobilier", null, new ArrayList<>());
            Category vehicules = new Category(null, "Véhicules", null, new ArrayList<>());
            Category emploi = new Category(null, "Emploi et Services", null, new ArrayList<>());
            Category electronique = new Category(null, "Électronique", null, new ArrayList<>());
            Category mode = new Category(null, "Mode et Beauté", null, new ArrayList<>());
            Category maison = new Category(null, "Maison et Jardin", null, new ArrayList<>());
            Category loisirs = new Category(null, "Loisirs et Divertissements", null, new ArrayList<>());
            Category materiel = new Category(null, "Matériel Professionnel", null, new ArrayList<>());
            Category animaux = new Category(null, "Animaux", null, new ArrayList<>());

            // Subcategories
            Category locations = new Category(null, "Locations", immobilier, new ArrayList<>());
            Category ventes = new Category(null, "Ventes", immobilier, new ArrayList<>());

            Category voitures = new Category(null, "Voitures", vehicules, new ArrayList<>());
            Category motos = new Category(null, "Motos et Scooters", vehicules, new ArrayList<>());

            Category offresEmploi = new Category(null, "Offres d’emploi", emploi, new ArrayList<>());
            Category demandesEmploi = new Category(null, "Demandes d’emploi", emploi, new ArrayList<>());

            Category telephones = new Category(null, "Téléphones et Tablettes", electronique, new ArrayList<>());
            Category ordinateurs = new Category(null, "Ordinateurs et Accessoires", electronique, new ArrayList<>());


			categoryRepository.saveAll(List.of(
                immobilier, vehicules, emploi, electronique, mode, maison, loisirs, materiel, animaux,
                locations, ventes, voitures, motos, offresEmploi, demandesEmploi, telephones, ordinateurs
            ));
		System.out.println("Database initialized!");
	}

	

}
