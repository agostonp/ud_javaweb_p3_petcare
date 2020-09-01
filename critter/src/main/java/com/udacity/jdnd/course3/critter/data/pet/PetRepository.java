package com.udacity.jdnd.course3.critter.data.pet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {
    public List<Pet> findAllByOwnerId(Long id);
}
