package com.udacity.jdnd.course3.critter.controller.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTO2Pet(petDTO);
        Long petId = petService.save(pet);
        petDTO.setId(petId);
        return petDTO;
    }

    @PostMapping("/{petId}")
    public PetDTO savePetWithIdInPath(@PathVariable long petId, @RequestBody PetDTO petDTO){
        petDTO.setId(petId);
        return savePet(petDTO);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable long petId) {
        try {
            Pet pet = petService.get(petId);
            PetDTO petDTO = convertPet2PetDTO(pet);
            return new ResponseEntity<>(petDTO, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(new PetDTO(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAll();
        return convertPetList2PetDTOList(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getAllByOwnerId(ownerId);
        return convertPetList2PetDTOList(pets);
    }

    private static PetDTO convertPet2PetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    private static List<PetDTO> convertPetList2PetDTOList(List<Pet> pets) {
        List<PetDTO> petDTOs = new ArrayList<>();
        if(pets != null) {
            for(Pet pet : pets) {
                petDTOs.add(convertPet2PetDTO(pet));
            }
        }
        return petDTOs;
    }

    private static Pet convertPetDTO2Pet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if(petDTO.getOwnerId() > 0L) {
            Customer owner = new Customer();
            owner.setId(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        return pet;
    }

}
