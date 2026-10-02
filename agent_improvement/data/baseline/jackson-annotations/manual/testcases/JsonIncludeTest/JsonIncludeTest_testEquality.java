package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testEquality extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testEquality() {
        assertTrue(EMPTY.equals(EMPTY));
        JsonInclude.Value v1 = JsonInclude.Value.construct(Include.NON_ABSENT, null);
        JsonInclude.Value v2 = JsonInclude.Value.construct(Include.NON_ABSENT, null);
        JsonInclude.Value v3 = JsonInclude.Value.construct(Include.NON_ABSENT, Include.NON_EMPTY);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertFalse(v1.equals(v3));
        assertFalse(v3.equals(v1));
        assertFalse(v2.equals(v3));
        assertFalse(v3.equals(v2));
    }
}
