package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;

import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Long save(Pet pet) {
        return petRepository.save(pet).getId();
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