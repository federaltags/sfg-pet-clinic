package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      PetService petService,
                      SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) {
        if (petTypeService.findAll().isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        var dog = new PetType();
        dog.setName("dog");
        var savedDogType = petTypeService.save(dog);

        var cat = new PetType();
        cat.setName("cat");
        var savedCatPetType = petTypeService.save(cat);


        var owner1 = new Owner();
        owner1.setFirstName("Micheal");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("123456789");

        var mikesPet = new Pet();
        mikesPet.setPetType(savedDogType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");
        owner1.getPets().add(mikesPet);

        ownerService.save(owner1);

        var owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Brickerel");
        owner2.setCity("Miami");
        owner2.setTelephone("123456789");

        var savedOwner2 = ownerService.save(owner2);

        var fionasCat = new Pet();
        fionasCat.setName("Just Cat");
        fionasCat.setOwner(savedOwner2);
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setPetType(savedCatPetType);
        owner2.getPets().add(fionasCat);
        var savedFionasCat = petService.save(fionasCat);

        System.out.println("Loading owners...");

        var radiology = new Speciality();
        radiology.setDescription("radiology");
        var savedRadiology = specialityService.save(radiology);

        var surgery = new Speciality();
        surgery.setDescription("surgery");
        var savedSurgery = specialityService.save(surgery);

        var dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        var savedDentistry = specialityService.save(dentistry);

        var vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        var vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loading vets...");

        Visit catVisit = new Visit();
        catVisit.setPet(savedFionasCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("sneezy kitty");
        visitService.save(catVisit);
    }
}
