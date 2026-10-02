package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testFactoryMethods extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testFactoryMethods() {
        JsonAutoDetect.Value v = JsonAutoDetect.Value.construct(PropertyAccessor.FIELD, Visibility.ANY);
        assertEquals(Visibility.ANY, v.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, v.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, v.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, v.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, v.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, v.getScalarConstructorVisibility());
        JsonAutoDetect.Value all = JsonAutoDetect.Value.construct(PropertyAccessor.ALL, Visibility.NONE);
        assertEquals(Visibility.NONE, all.getFieldVisibility());
        assertEquals(Visibility.NONE, all.getGetterVisibility());
        assertEquals(Visibility.NONE, all.getIsGetterVisibility());
        assertEquals(Visibility.NONE, all.getSetterVisibility());
        assertEquals(Visibility.NONE, all.getCreatorVisibility());
        assertEquals(Visibility.NONE, all.getScalarConstructorVisibility());
    }
}
