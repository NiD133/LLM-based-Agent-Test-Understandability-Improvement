package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonTypeInfo.Value#from(JsonTypeInfo)}, specifically
 * the contract that a null annotation input maps to a null Value (meaning
 * "no type info configured"), which is distinct from the EMPTY sentinel
 * that represents a present-but-unconfigured annotation.
 */
public class JsonTypeInfoTest_testEmpty extends AnnotationTestUtil {

    @Test
    public void testFromNullAnnotationReturnsNull() {
        // null annotation means "no @JsonTypeInfo present at all"; Value.from must
        // return null so callers can distinguish absent annotation from EMPTY value
        assertNull(JsonTypeInfo.Value.from(null),
                "Value.from(null) should return null to indicate no annotation is present");
    }
}
