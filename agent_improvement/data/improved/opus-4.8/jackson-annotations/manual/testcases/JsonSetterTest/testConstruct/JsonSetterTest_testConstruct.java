package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies the null-handling shortcut of {@link JsonSetter.Value#construct}.
 */
public class JsonSetterTest_testConstruct extends AnnotationTestUtil {

    /** The canonical "no custom settings" instance returned by {@link JsonSetter.Value#empty()}. */
    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void construct_withNullNullsArguments_returnsSharedEmptyInstance() throws Exception {
        // Passing null for both null-handling arguments means "use defaults",
        // which construct() collapses to the shared EMPTY singleton.
        JsonSetter.Value constructed = JsonSetter.Value.construct(null, null);

        assertSame(emptyValue, constructed);
    }
}
