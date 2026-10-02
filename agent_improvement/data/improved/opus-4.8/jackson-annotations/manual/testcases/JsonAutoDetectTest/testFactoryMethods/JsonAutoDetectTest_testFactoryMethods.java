package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies the {@link Value#construct(PropertyAccessor, Visibility)} factory method:
 * the chosen accessor (or every accessor, for {@link PropertyAccessor#ALL}) takes the
 * requested visibility, while all other accessors stay at {@link Visibility#DEFAULT}.
 */
public class JsonAutoDetectTest_testFactoryMethods extends AnnotationTestUtil {

    @Test
    public void testFactoryMethods() {
        // construct(FIELD, ANY): only the field accessor is overridden; the rest stay DEFAULT.
        Value fieldOnly = Value.construct(PropertyAccessor.FIELD, Visibility.ANY);
        assertEquals(Visibility.ANY, fieldOnly.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getScalarConstructorVisibility());

        // construct(ALL, NONE): every accessor is overridden with the same visibility.
        Value allAccessors = Value.construct(PropertyAccessor.ALL, Visibility.NONE);
        assertEquals(Visibility.NONE, allAccessors.getFieldVisibility());
        assertEquals(Visibility.NONE, allAccessors.getGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getIsGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getSetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getCreatorVisibility());
        assertEquals(Visibility.NONE, allAccessors.getScalarConstructorVisibility());
    }
}
