package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testStdOverrides extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testStdOverrides() {
        assertEquals("JsonInclude.Value(value=NON_ABSENT,content=USE_DEFAULTS)", JsonInclude.Value.construct(Include.NON_ABSENT, null).toString());
        int x = EMPTY.hashCode();
        if (x == 0) {
            fail();
        }
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals(""));
    }
}
