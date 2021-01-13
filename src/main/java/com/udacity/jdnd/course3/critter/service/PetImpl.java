package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetImpl {


    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Pet save(Pet pet){
        pet = petRepository.save(pet);
        return pet;
    }

    public Pet getPetById(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllByOwnerId(long ownerId) {
        List<Pet> pets;

        Optional<Customer> customerOptional = customerRepository.findById(ownerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pets = customer.getPets();
        } else {
            pets = new ArrayList<>();
        }
        return pets;
    }

    public List<Pet> getAllPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }

    public List<Pet> findAllById(List<Long> petIds){
        return petRepository.findAllById(petIds);
    }

    public Pet findById(long petId){
        return petRepository.findById(petId).orElseThrow(()-> new ObjectNotFoundException("Pet not found"));
    }
}
