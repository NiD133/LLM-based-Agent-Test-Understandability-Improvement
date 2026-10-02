package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonSetter.Value}'s {@code withXxx} mutant factories
 * apply each null-handling setting independently, starting from an empty value.
 */
public class JsonSetterTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void testSimpleMerge() {
        // Overriding the content-null handling should set only that setting.
        JsonSetter.Value withContentSkip = emptyValue.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withContentSkip.getContentNulls());

        // Overriding the value-null handling on top should set only that setting.
        JsonSetter.Value withValueFail = withContentSkip.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withValueFail.getValueNulls());
    }
}
