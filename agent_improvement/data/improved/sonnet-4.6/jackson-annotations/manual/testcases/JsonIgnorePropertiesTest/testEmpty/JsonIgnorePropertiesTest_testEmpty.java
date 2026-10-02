package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIgnoreProperties.Value#empty()}, verifying that the
 * canonical empty Value instance has all properties at their "off" defaults.
 */
public class JsonIgnorePropertiesTest_testEmpty extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    @DisplayName("empty Value: from(null) returns the canonical EMPTY singleton with no ignored properties and no getter/setter allowances")
    public void testEmpty() {
        // Value.from(null) is documented to return the canonical EMPTY instance
        assertSame(EMPTY, JsonIgnoreProperties.Value.from(null),
                "Value.from(null) should return the canonical EMPTY singleton");

        // Empty value must not list any properties to ignore
        assertEquals(0, EMPTY.getIgnored().size(),
                "Empty Value should have no ignored property names");

        // By default, getters of ignored properties are also ignored (allowGetters=false)
        assertFalse(EMPTY.getAllowGetters(),
                "Empty Value should not allow getters (getters of ignored props must also be ignored)");

        // By default, setters of ignored properties are also ignored (allowSetters=false)
        assertFalse(EMPTY.getAllowSetters(),
                "Empty Value should not allow setters (setters of ignored props must also be ignored)");
    }
}
