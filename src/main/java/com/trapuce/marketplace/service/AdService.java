package com.trapuce.marketplace.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trapuce.marketplace.dtos.AdPostDto;
import com.trapuce.marketplace.dtos.ElectronicAttributesDto;
import com.trapuce.marketplace.dtos.LocationDto;
import com.trapuce.marketplace.dtos.ProfessionalEquipmentAttributesDto;
import com.trapuce.marketplace.dtos.RealEstateAttributesDto;
import com.trapuce.marketplace.dtos.VehicleAttributesDto;
import com.trapuce.marketplace.models.Ad;
import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.models.ElectronicAttributes;
import com.trapuce.marketplace.models.Location;
import com.trapuce.marketplace.models.Photo;
import com.trapuce.marketplace.models.ProfessionalEquipmentAttributes;
import com.trapuce.marketplace.models.RealEstateAttributes;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.models.VehicleAttributes;
import com.trapuce.marketplace.repository.AdRepository;
import com.trapuce.marketplace.repository.CategoryRepository;
import com.trapuce.marketplace.repository.LocationRepository;
import com.trapuce.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdService {

    @Autowired
    public AdRepository adRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public LocationRepository locationRepository;

    @Transactional
    public Ad createAdForUser(Long userId, AdPostDto adPostDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Category category = categoryRepository.findById(adPostDto.getCategoryId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Category not found with ID: " + adPostDto.getCategoryId()));

        Ad ad = mapToAd(adPostDto);

        Location location = mapToLocation(adPostDto.getLocation());

        user.addAd(ad);
        category.addAd(ad);
        location.addAd(ad);

        if (isVehicleCategory(category)) {
            VehicleAttributes vehicleAttributes = mapToVehicleAttributes(adPostDto.getVehicleAttributes());
            vehicleAttributes.setAd(ad);
            ad.setVehicleAttributes(vehicleAttributes);
        } else if (isRealEstateCategory(category)) {
            RealEstateAttributes realEstateAttributes = mapToRealEstateAttributes(adPostDto.getRealEstateAttributes());
            realEstateAttributes.setAd(ad);
            ad.setRealEstateAttributes(realEstateAttributes);
        }

        else if (isProfessionalEquipmentCategory(category)) {
            ProfessionalEquipmentAttributes professionalEquipmentAttributes = mapToProfessionalEquipmentAttributes(
                    adPostDto.getProfessionalEquipmentAttributesDto());
            professionalEquipmentAttributes.setAd(ad);
            ad.setProfessionalEquipmentAttributes(professionalEquipmentAttributes);
        } else if (isElectronicAttributes(category)) {
            ElectronicAttributes electronicAttributes = mapToElectronicAttributes(adPostDto.getElectronicAttributes());
            electronicAttributes.setAd(ad);
            ad.setElectronicAttributes(electronicAttributes);
        }
        if (adPostDto.getPhotos() != null && !adPostDto.getPhotos().isEmpty()) {
            for (byte[] photoContent : adPostDto.getPhotos()) {
                Photo photo = new Photo();
                photo.setContent(photoContent);
                ad.addPhoto(photo);
            }
        }
        
        locationRepository.save(location);

        return adRepository.save(ad);
    }

    private Ad mapToAd(AdPostDto adPostDto) {
        Ad ad = new Ad();
        ad.setTitle(adPostDto.getTitle());
        ad.setDescription(adPostDto.getDescription());
        ad.setIs_active(true);
        ad.setIs_urgent(true);
        ad.setPrice(adPostDto.getPrice());
        ad.setPublication_date(new Date());
        ad.setStatus(adPostDto.getStatus());
        return ad;
    }

    private Location mapToLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setCity(locationDto.getCity());
        location.setDepartment(locationDto.getDepartment());
        location.setRegion(locationDto.getRegion());
        location.setPostal_code(locationDto.getPostal_code());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return location;
    }

    private VehicleAttributes mapToVehicleAttributes(VehicleAttributesDto vehicleDto) {
        VehicleAttributes attributes = new VehicleAttributes();
        attributes.setRegistrationNumber(vehicleDto.getRegistrationNumber());
        attributes.setBrand(vehicleDto.getBrand());
        attributes.setModel(vehicleDto.getModel());
        attributes.setYear(vehicleDto.getYear());
        attributes.setMileage(vehicleDto.getMileage());
        attributes.setFuelType(vehicleDto.getFuelType());
        attributes.setGearbox(vehicleDto.getGearbox());
        attributes.setFiscalPower(vehicleDto.getFiscalPower());
        attributes.setIsFirstHand(vehicleDto.getIsFirstHand());
        attributes.setTechnicalControl(vehicleDto.getTechnicalControl());
        attributes.setVehicleType(vehicleDto.getVehicleType());
        return attributes;
    }

    private RealEstateAttributes mapToRealEstateAttributes(RealEstateAttributesDto realEstateAttributesDto) {
        RealEstateAttributes realEstateAttributes = new RealEstateAttributes();
        realEstateAttributes.setHabitableSurface(realEstateAttributesDto.getHabitableSurface());
        realEstateAttributes.setNumberOfRooms(realEstateAttributesDto.getNumberOfRooms());
        realEstateAttributes.setNumberOfBedrooms(realEstateAttributesDto.getNumberOfBedrooms());
        realEstateAttributes.setNumberOfBathrooms(realEstateAttributesDto.getNumberOfBathrooms());
        realEstateAttributes.setPropertyType(realEstateAttributesDto.getPropertyType());
        realEstateAttributes.setFloor(realEstateAttributesDto.getFloor());
        realEstateAttributes.setHasElevator(realEstateAttributesDto.getHasElevator());
        realEstateAttributes.setEnergyPerformance(realEstateAttributesDto.getEnergyPerformance());
        realEstateAttributes.setHeatingType(realEstateAttributesDto.getHeatingType());
        realEstateAttributes.setFurnished(realEstateAttributesDto.getFurnished());
        realEstateAttributes.setCharges(realEstateAttributesDto.getCharges());
        realEstateAttributes.setTransactionType(realEstateAttributesDto.getTransactionType());
        return realEstateAttributes;
    }

    private ProfessionalEquipmentAttributes mapToProfessionalEquipmentAttributes(
            ProfessionalEquipmentAttributesDto professionalEquipmentAttributesDto) {
        ProfessionalEquipmentAttributes professionalEquipmentAttributes = new ProfessionalEquipmentAttributes();
        professionalEquipmentAttributes.setEquipmentType(professionalEquipmentAttributesDto.getEquipmentType());
        professionalEquipmentAttributes.setBrand(professionalEquipmentAttributesDto.getBrand());
        professionalEquipmentAttributes.setModel(professionalEquipmentAttributesDto.getModel());
        professionalEquipmentAttributes.setCondition(professionalEquipmentAttributesDto.getCondition());
        professionalEquipmentAttributes.setYear(professionalEquipmentAttributesDto.getYear());
        professionalEquipmentAttributes.setCertification(professionalEquipmentAttributesDto.getCertification());
        professionalEquipmentAttributes.setWarranty(professionalEquipmentAttributesDto.getWarranty());
        professionalEquipmentAttributes.setUsage(professionalEquipmentAttributesDto.getUsage());
        return professionalEquipmentAttributes;
    }

    private ElectronicAttributes mapToElectronicAttributes(ElectronicAttributesDto electronicAttributesDto) {
        ElectronicAttributes electronicAttributes = new ElectronicAttributes();

        electronicAttributes.setBrand(electronicAttributesDto.getBrand());
        electronicAttributes.setModel(electronicAttributesDto.getModel());
        electronicAttributes.setCondition(electronicAttributesDto.getCondition());
        electronicAttributes.setCapacity(electronicAttributesDto.getCapacity());
        electronicAttributes.setColor(electronicAttributesDto.getColor());
        electronicAttributes.setOperatingSystem(electronicAttributesDto.getOperatingSystem());
        electronicAttributes.setProcessor(electronicAttributesDto.getProcessor());
        electronicAttributes.setRam(electronicAttributesDto.getRam());
        electronicAttributes.setStorage(electronicAttributesDto.getStorage());
        electronicAttributes.setScreenResolution(electronicAttributesDto.getScreenResolution());
        electronicAttributes.setWarranty(electronicAttributesDto.getWarranty());

        return electronicAttributes;
    }

    private boolean isVehicleCategory(Category category) {
        return "Véhicules".equals(category.getName());
    }

    private boolean isRealEstateCategory(Category category) {
        return "Immobilier".equals(category.getName());
    }

    private boolean isProfessionalEquipmentCategory(Category category) {
        return "Matériel Professionnel".equals(category.getName());
    }

    private boolean isElectronicAttributes(Category category) {
        return "Électronique".equals(category.getName());
    }

    public Ad getAdById(Long adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Transactional
    public void addPhotoToAd(Long adId, MultipartFile file, int order) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));

        try {
            Photo photo = Photo.builder()
                    .ad(ad)
                    .content(file.getBytes())
                    .order(order)
                    .build();

            ad.addPhoto(photo);
            adRepository.save(ad);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file content", e);
        }
    }

    @Transactional
    public void deleteAdById(Long adId) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));

        cleanupAdRelationships(ad);

        adRepository.delete(ad);
    }

    private void cleanupAdRelationships(Ad ad) {
        ad.setVehicleAttributes(null);
        ad.setRealEstateAttributes(null);
        ad.setProfessionalEquipmentAttributes(null);
        ad.setElectronicAttributes(null);

        if (ad.getLocation() != null) {
            Location location = ad.getLocation();
            location.removeAd(ad);
            locationRepository.delete(location);
        }

        if (ad.getUser() != null) {
            ad.getUser().removeAd(ad);
        }

        if (ad.getCategory() != null) {
            ad.getCategory().removeAd(ad);
        }
    }

}
