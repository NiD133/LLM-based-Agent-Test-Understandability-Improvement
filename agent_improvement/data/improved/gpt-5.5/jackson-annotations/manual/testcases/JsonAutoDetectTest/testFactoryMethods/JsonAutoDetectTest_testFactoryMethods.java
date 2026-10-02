package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonAutoDetectTest_testFactoryMethods extends AnnotationTestUtil {

    @Test
    public void testFactoryMethods() {
        JsonAutoDetect.Value fieldVisibility =
                JsonAutoDetect.Value.construct(PropertyAccessor.FIELD, Visibility.ANY);
        assertVisibility(fieldVisibility,
                Visibility.ANY,
                Visibility.DEFAULT,
                Visibility.DEFAULT,
                Visibility.DEFAULT,
                Visibility.DEFAULT,
                Visibility.DEFAULT);

        JsonAutoDetect.Value allVisibility =
                JsonAutoDetect.Value.construct(PropertyAccessor.ALL, Visibility.NONE);
        assertVisibility(allVisibility,
                Visibility.NONE,
                Visibility.NONE,
                Visibility.NONE,
                Visibility.NONE,
                Visibility.NONE,
                Visibility.NONE);
    }

    private void assertVisibility(JsonAutoDetect.Value actual,
            Visibility expectedField,
            Visibility expectedGetter,
            Visibility expectedIsGetter,
            Visibility expectedSetter,
            Visibility expectedCreator,
            Visibility expectedScalarConstructor) {
        assertEquals(expectedField, actual.getFieldVisibility());
        assertEquals(expectedGetter, actual.getGetterVisibility());
        assertEquals(expectedIsGetter, actual.getIsGetterVisibility());
        assertEquals(expectedSetter, actual.getSetterVisibility());
        assertEquals(expectedCreator, actual.getCreatorVisibility());
        assertEquals(expectedScalarConstructor, actual.getScalarConstructorVisibility());
    }
}
