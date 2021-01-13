package com.udacity.jdnd.course3.critter.pet;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PetTypeConverter implements AttributeConverter<PetType,String> {

    @Override
    public String convertToDatabaseColumn(PetType petType) {
        if(petType == null) {
            return null;
        }

        return petType.getCode();
    }

    @Override
    public PetType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }

        return Stream.of(PetType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
