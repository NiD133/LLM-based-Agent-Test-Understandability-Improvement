package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonIgnorePropertiesTest_testToString extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

    @Test
    public void testToString() {
        JsonIgnoreProperties.Value valueWithAllowedSettersAndMerge = emptyValue
                .withAllowSetters()
                .withMerge();

        assertEquals(
                "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=true,merge=true)",
                valueWithAllowedSettersAndMerge.toString());

        int hash = emptyValue.hashCode();
        if (hash == 0) {
            fail("Should not get 0 for hash");
        }
    }
}
