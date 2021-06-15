package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;


    @Autowired
    public PetService(PetRepository petRepository, UserRepository userRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
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
        pet.getCustomer().getPets().add(pet);
        return petRepository.save(pet);
    }

    public List<Pet> getPetByOwner(Long ownerId) {
        if(!customerRepository.existsById(ownerId)) {
            throw new IllegalStateException("Id not mapped to any owner");
        }
        return petRepository.findByCustomerId(ownerId);
    }
}
