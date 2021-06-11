package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
//@RestController
//@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public PetController(PetService petService, CustomerService customerService, UserService userService) {
        this.petService = petService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.createPet(convertPetDTOToEntity(petDTO));
        return convertPetEntityToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return convertPetListToDTOs(pets);
    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetByOwner(ownerId);
        return convertPetListToDTOs(pets);
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setType(petDTO.getType());
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertPetEntityToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    private List<PetDTO> convertPetListToDTOs(List<Pet> pets) {
        List<PetDTO> petDTOs = new ArrayList<>();

        for(Pet p : pets) {
            petDTOs.add(convertPetEntityToDTO(p));
        }
        return petDTOs;
    }
}
