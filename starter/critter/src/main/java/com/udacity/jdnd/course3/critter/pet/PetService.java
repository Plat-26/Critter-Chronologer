package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;


    @Autowired
    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }


    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new IllegalStateException("Pet id not in database")
        );
    }

    @Transactional
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getPetByOwner(Long ownerId) {
        if(!userRepository.existsById(ownerId)) {
            throw new IllegalStateException("Id not mapped to any owner");
        }
        return petRepository.findAllByCustomer(ownerId);
    }
}
