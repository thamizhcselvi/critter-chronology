package com.udacity.jdnd.course3.critter.pet;

/**
 * A example list of pet type metadata that could be included on a request to create a pet.
 */
public enum PetType {
    CAT("C"), DOG("D"), LIZARD("L"), BIRD("B"), FISH("F"), SNAKE("S"), OTHER("O");

    private String code;

    private PetType(String code)
    {
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
