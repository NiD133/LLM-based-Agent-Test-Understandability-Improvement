package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testStdMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testToString_emptyValue_includesBothNullsAsDefault() {
        assertEquals(
            "JsonSetter.Value(valueNulls=DEFAULT,contentNulls=DEFAULT)",
            EMPTY.toString()
        );
    }

    @Test
    public void testHashCode_emptyValue_returnsNonZero() {
        int hashCode = EMPTY.hashCode();
        assertNotEquals(0, hashCode, "hashCode() must not return 0 for the empty Value");
    }

    @Test
    public void testEquals_sameInstance_isEqualToItself() {
        assertEquals(EMPTY, EMPTY);
    }

    @Test
    public void testEquals_nullArgument_returnsFalse() {
        assertFalse(EMPTY.equals(null));
    }

    @Test
    public void testEquals_differentType_returnsFalse() {
        assertFalse(EMPTY.equals("xyz"));
    }
}
