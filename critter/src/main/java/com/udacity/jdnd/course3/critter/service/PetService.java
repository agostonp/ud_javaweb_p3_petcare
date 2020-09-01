package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;

import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerService customerService;

    public PetService(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public Long save(Pet pet) {
        Pet newPet = petRepository.save(pet);

        customerService.addPetToCustomer(newPet);

        return newPet.getId();
    }

    public Pet get(Long id) {
        return petRepository.findById(id).orElseThrow();
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getAllByOwnerId(Long id) {
        return petRepository.findAllByOwnerId(id);
    }

}