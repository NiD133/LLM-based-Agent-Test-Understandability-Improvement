package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testSimpleMerge extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testSimpleMerge() {
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(Visibility.ANY, Visibility.PUBLIC_ONLY, Visibility.ANY, Visibility.NONE, Visibility.ANY, Visibility.PROTECTED_AND_PUBLIC);
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(Visibility.NON_PRIVATE, Visibility.DEFAULT, Visibility.PUBLIC_ONLY, Visibility.DEFAULT, Visibility.DEFAULT, Visibility.PUBLIC_ONLY);
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);
        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);
        assertEquals(Visibility.NON_PRIVATE, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());
        // try the other way around too
        merged = JsonAutoDetect.Value.merge(overrides, base);
        assertEquals(Visibility.ANY, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.ANY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, merged.getScalarConstructorVisibility());
        // plus, special cases
        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }
}
