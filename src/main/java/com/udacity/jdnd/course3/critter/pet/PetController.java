package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetImpl;
import com.udacity.jdnd.course3.critter.service.UserImpl;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetImpl petImpl;
    @Autowired
    UserImpl userImpl;

    @RequestMapping(method = RequestMethod.POST)
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = new Pet();
        Customer customer = userImpl.findById(petDTO.getOwnerId());

        BeanUtils.copyProperties(petDTO,pet);

        pet.setPetType(petDTO.getType());
        pet.setCustomer(customer);

        Pet saved = petImpl.save(pet);
        if(customer.getPets()==null){
            customer.setPets(new ArrayList<>());
        }
        customer.getPets().add(saved);
        BeanUtils.copyProperties(saved,petDTO);
        return petDTO;
    }
//    @PostMapping("/{ownerId}")
//    public PetDTO savePet(@RequestBody PetDTO petDTO,@PathVariable("ownerId") Long ownerId){
//        petDTO.setOwnerId(ownerId);
//        return savePet(petDTO);
//    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetDTO(petImpl.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petImpl.getAllPets();
        return pets.stream()
                .map(this::getPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = userImpl.findById(ownerId);
        List<Pet> pets = customer.getPets();
        return pets.stream()
                .map(this::getPetDTO)
                .collect(Collectors.toList());
    }

    private PetDTO getPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getPetType());
        return petDTO;
    }
}
