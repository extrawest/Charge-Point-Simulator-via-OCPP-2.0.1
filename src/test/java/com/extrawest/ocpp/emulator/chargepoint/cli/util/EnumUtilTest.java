package com.extrawest.ocpp.emulator.chargepoint.cli.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumUtilTest {

    @Getter
    @AllArgsConstructor
    private enum Animal {
        CAT("cat"),
        DOG("dog"),
        FISH("fish");

        private final String value;

    }

    @Test
    void findByFieldTest(){
        assertEquals(Animal.CAT, EnumUtil.findByField(Animal.class, Animal::getValue, "cat"));
        assertEquals(Animal.DOG, EnumUtil.findByField(Animal.class, Animal::getValue, "dog"));
        assertEquals(Animal.FISH, EnumUtil.findByField(Animal.class, Animal::getValue, "fish"));
    }

}