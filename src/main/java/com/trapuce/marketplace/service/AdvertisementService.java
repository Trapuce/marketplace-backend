package com.trapuce.marketplace.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.AdvertisementPostDto;
import com.trapuce.marketplace.dtos.ElectronicAttributesDto;
import com.trapuce.marketplace.dtos.LocationDto;
import com.trapuce.marketplace.dtos.ProfessionalEquipmentAttributesDto;
import com.trapuce.marketplace.dtos.RealEstateAttributesDto;
import com.trapuce.marketplace.dtos.VehicleAttributesDto;
import com.trapuce.marketplace.models.Advertisement;
import com.trapuce.marketplace.models.Category;
import com.trapuce.marketplace.models.ElectronicAttributes;
import com.trapuce.marketplace.models.Location;
import com.trapuce.marketplace.models.ProfessionalEquipmentAttributes;
import com.trapuce.marketplace.models.RealEstateAttributes;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.models.VehicleAttributes;
import com.trapuce.marketplace.repository.AdvertisementRepository;
import com.trapuce.marketplace.repository.CategoryRepository;
import com.trapuce.marketplace.repository.LocationRepository;
import com.trapuce.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, 
                                CategoryRepository categoryRepository, 
                                UserRepository userRepository, 
                                LocationRepository locationRepository) {
        this.advertisementRepository = advertisementRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Advertisement createAdForUser(Long userId, AdvertisementPostDto advertisementPostDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Category category = categoryRepository.findById(advertisementPostDto.getCategoryId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Category not found with ID: " + advertisementPostDto.getCategoryId()));

                        Advertisement advertisement = mapToAd(advertisementPostDto);

        Location location = mapToLocation(advertisementPostDto.getLocation());

        user.addAdvertisement(advertisement);
        category.addAdvertisement(advertisement);
        location.addAdvertisement(advertisement);

        if (isVehicleCategory(category)) {
            VehicleAttributes vehicleAttributes = mapToVehicleAttributes(advertisementPostDto.getVehicleAttributes());
            vehicleAttributes.setAdvertisement(advertisement);
            advertisement.setVehicleAttributes(vehicleAttributes);
        } else if (isRealEstateCategory(category)) {
            RealEstateAttributes realEstateAttributes = mapToRealEstateAttributes(advertisementPostDto.getRealEstateAttributes());
            realEstateAttributes.setAdvertisement(advertisement);
            advertisement.setRealEstateAttributes(realEstateAttributes);
        }

        else if (isProfessionalEquipmentCategory(category)) {
            ProfessionalEquipmentAttributes professionalEquipmentAttributes = mapToProfessionalEquipmentAttributes(
                advertisementPostDto.getProfessionalEquipmentAttributesDto());
            professionalEquipmentAttributes.setAdvertisement(advertisement);
            advertisement.setProfessionalEquipmentAttributes(professionalEquipmentAttributes);
        } else if (isElectronicAttributes(category)) {
            ElectronicAttributes electronicAttributes = mapToElectronicAttributes(advertisementPostDto.getElectronicAttributes());
            electronicAttributes.setAdvertisement(advertisement);
            advertisement.setElectronicAttributes(electronicAttributes);
        }
        // if (adPostDto.getPhotos() != null && !adPostDto.getPhotos().isEmpty()) {
        //     for (byte[] photoContent : adPostDto.getPhotos()) {
        //         Photo photo = new Photo();
        //         photo.setContent(photoContent);
        //         ad.addPhoto(photo);
        //     }
        // }
        
        locationRepository.save(location);

        return advertisementRepository.save(advertisement);
    }

    private Advertisement mapToAd(AdvertisementPostDto advertisementPostDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementPostDto.getTitle());
        advertisement.setDescription(advertisementPostDto.getDescription());
        advertisement.setIs_active(true);
        advertisement.setIs_urgent(true);
        advertisement.setPrice(advertisementPostDto.getPrice());
        advertisement.setPublication_date(new Date());
        advertisement.setStatus(advertisementPostDto.getStatus());
        return advertisement;
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

    public Advertisement getAdById(Long adId) {
        return advertisementRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));
    }

    public List<Advertisement> getAllAds() {
        return advertisementRepository.findAll();
    }

    // @Transactional
    // public void addPhotoToAd(Long adId, MultipartFile file, int order) {
    //     Ad ad = adRepository.findById(adId)
    //             .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));

    //     try {
    //         Photo photo = Photo.builder()
    //                 .ad(ad)
    //                 .content(file.getBytes())
    //                 .order(order)
    //                 .build();

    //         ad.addPhoto(photo);
    //         adRepository.save(ad);
    //     } catch (IOException e) {
    //         throw new RuntimeException("Error reading file content", e);
    //     }
    // }

    @Transactional
    public void deleteAdById(Long adId) {
        Advertisement ad = advertisementRepository.findById(adId)
                .orElseThrow(() -> new IllegalArgumentException("Ad not found with ID: " + adId));

        cleanupAdRelationships(ad);

        advertisementRepository.delete(ad);
    }

    private void cleanupAdRelationships(Advertisement advertisement) {
        advertisement.setVehicleAttributes(null);
        advertisement.setRealEstateAttributes(null);
        advertisement.setProfessionalEquipmentAttributes(null);
        advertisement.setElectronicAttributes(null);

        if (advertisement.getLocation() != null) {
            Location location = advertisement.getLocation();
            location.removeAdvertisement(advertisement);
            locationRepository.delete(location);
        }

        if (advertisement.getUser() != null) {
            advertisement.getUser().removeAdvertisement(advertisement);
        }

        if (advertisement.getCategory() != null) {
            advertisement.getCategory().removeAdvertisement(advertisement);
        }
    }

}
