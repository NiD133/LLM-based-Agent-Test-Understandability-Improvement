package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonSetter.Value#construct(Nulls, Nulls)}.
 */
public class JsonSetterTest_testConstruct extends AnnotationTestUtil {

    // The canonical "all defaults" singleton, used as the expected result in null-argument tests.
    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    /**
     * When both {@code nulls} and {@code contentNulls} are {@code null},
     * {@code construct} must treat them as {@link Nulls#DEFAULT} and return
     * the shared EMPTY singleton (not a new instance).
     */
    @Test
    public void testConstruct_withBothNullArguments_returnsEmptySingleton() {
        // null arguments are normalised to Nulls.DEFAULT, so both fields end up
        // as DEFAULT — identical to the EMPTY singleton.
        JsonSetter.Value result = JsonSetter.Value.construct(null, null);

        // Must be the exact same object as the EMPTY singleton (identity check).
        assertSame(EMPTY, result);
    }
}
