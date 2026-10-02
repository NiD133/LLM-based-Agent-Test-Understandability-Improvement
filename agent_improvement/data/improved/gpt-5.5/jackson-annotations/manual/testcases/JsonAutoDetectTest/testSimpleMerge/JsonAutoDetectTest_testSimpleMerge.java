package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonAutoDetectTest_testSimpleMerge extends AnnotationTestUtil {

    private static final JsonAutoDetect.Value NO_OVERRIDES =
            JsonAutoDetect.Value.noOverrides();

    private static final JsonAutoDetect.Value DEFAULTS =
            JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testSimpleMerge() {
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                Visibility.ANY,
                Visibility.PUBLIC_ONLY,
                Visibility.ANY,
                Visibility.NONE,
                Visibility.ANY,
                Visibility.PROTECTED_AND_PUBLIC);
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                Visibility.NON_PRIVATE,
                Visibility.DEFAULT,
                Visibility.PUBLIC_ONLY,
                Visibility.DEFAULT,
                Visibility.DEFAULT,
                Visibility.PUBLIC_ONLY);

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);

        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);
        assertVisibility(merged,
                Visibility.NON_PRIVATE,
                Visibility.PUBLIC_ONLY,
                Visibility.PUBLIC_ONLY,
                Visibility.NONE,
                Visibility.ANY,
                Visibility.PUBLIC_ONLY);

        merged = JsonAutoDetect.Value.merge(overrides, base);

        assertVisibility(merged,
                Visibility.ANY,
                Visibility.PUBLIC_ONLY,
                Visibility.ANY,
                Visibility.NONE,
                Visibility.ANY,
                Visibility.PROTECTED_AND_PUBLIC);

        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }

    private void assertVisibility(JsonAutoDetect.Value actual,
            Visibility fieldVisibility,
            Visibility getterVisibility,
            Visibility isGetterVisibility,
            Visibility setterVisibility,
            Visibility creatorVisibility,
            Visibility scalarConstructorVisibility) {
        assertEquals(fieldVisibility, actual.getFieldVisibility());
        assertEquals(getterVisibility, actual.getGetterVisibility());
        assertEquals(isGetterVisibility, actual.getIsGetterVisibility());
        assertEquals(setterVisibility, actual.getSetterVisibility());
        assertEquals(creatorVisibility, actual.getCreatorVisibility());
        assertEquals(scalarConstructorVisibility,
                actual.getScalarConstructorVisibility());
    }
}
