package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testFactoryMethods extends AnnotationTestUtil {

    /**
     * When constructing a Value for a single accessor (FIELD), only that accessor's
     * visibility should be set; all other accessors must remain at DEFAULT.
     */
    @Test
    public void testConstructWithSingleAccessor_onlyTargetedAccessorIsChanged() {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(PropertyAccessor.FIELD, Visibility.ANY);

        assertEquals(Visibility.ANY,     value.getFieldVisibility(),             "FIELD should be set to ANY");
        assertEquals(Visibility.DEFAULT, value.getGetterVisibility(),            "getter should remain DEFAULT");
        assertEquals(Visibility.DEFAULT, value.getIsGetterVisibility(),          "is-getter should remain DEFAULT");
        assertEquals(Visibility.DEFAULT, value.getSetterVisibility(),            "setter should remain DEFAULT");
        assertEquals(Visibility.DEFAULT, value.getCreatorVisibility(),           "creator should remain DEFAULT");
        assertEquals(Visibility.DEFAULT, value.getScalarConstructorVisibility(), "scalar-constructor should remain DEFAULT");
    }

    /**
     * When constructing a Value using the ALL accessor, every individual accessor
     * should be assigned the specified visibility (NONE here).
     */
    @Test
    public void testConstructWithAllAccessors_everyAccessorIsSetToGivenVisibility() {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(PropertyAccessor.ALL, Visibility.NONE);

        assertEquals(Visibility.NONE, value.getFieldVisibility(),             "FIELD should be NONE");
        assertEquals(Visibility.NONE, value.getGetterVisibility(),            "getter should be NONE");
        assertEquals(Visibility.NONE, value.getIsGetterVisibility(),          "is-getter should be NONE");
        assertEquals(Visibility.NONE, value.getSetterVisibility(),            "setter should be NONE");
        assertEquals(Visibility.NONE, value.getCreatorVisibility(),           "creator should be NONE");
        assertEquals(Visibility.NONE, value.getScalarConstructorVisibility(), "scalar-constructor should be NONE");
    }
}
