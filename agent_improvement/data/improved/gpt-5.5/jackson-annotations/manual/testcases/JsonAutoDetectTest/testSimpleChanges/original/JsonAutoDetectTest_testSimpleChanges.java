package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testSimpleChanges extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testSimpleChanges() {
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));
        JsonAutoDetect.Value v = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, v);
        assertEquals(Visibility.PUBLIC_ONLY, v.getCreatorVisibility());
        v = NO_OVERRIDES.withFieldVisibility(Visibility.ANY);
        assertEquals(Visibility.ANY, v.getFieldVisibility());
        v = NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE);
        assertEquals(Visibility.NON_PRIVATE, v.getGetterVisibility());
        v = NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC);
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, v.getIsGetterVisibility());
        v = NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, v.getSetterVisibility());
        v = NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, v.getScalarConstructorVisibility());
    }
}
