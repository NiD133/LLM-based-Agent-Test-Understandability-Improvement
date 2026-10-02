package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testEmpty extends AnnotationTestUtil {

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testEmpty() {
        assertNull(EMPTY.getId());
        assertNull(EMPTY.getUseInput());
        assertTrue(EMPTY.willUseInput(true));
        assertFalse(EMPTY.willUseInput(false));
        assertSame(EMPTY, JacksonInject.Value.construct(null, null, null));
        // also, "" gets coerced to null so
        assertSame(EMPTY, JacksonInject.Value.construct("", null, null));
    }
}
