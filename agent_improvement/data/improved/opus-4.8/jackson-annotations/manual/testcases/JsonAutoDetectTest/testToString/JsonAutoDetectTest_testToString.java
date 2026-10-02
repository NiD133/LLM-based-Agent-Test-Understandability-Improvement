package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the human-readable {@link JsonAutoDetect.Value#toString()} output for the two
 * predefined {@code Value} instances: the baseline "default visibility" instance and the
 * "no overrides" instance (where every accessor visibility is {@code DEFAULT}).
 */
public class JsonAutoDetectTest_testToString extends AnnotationTestUtil {

    @Test
    public void testToString() {
        // Default visibility: public fields/getters/is-getters/creators, any setters,
        // non-private scalar constructors.
        String expectedDefaultVisibility =
                "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,"
                        + "isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)";
        assertEquals(expectedDefaultVisibility,
                JsonAutoDetect.Value.defaultVisibility().toString());

        // No overrides: every accessor visibility is left as DEFAULT.
        String expectedNoOverrides =
                "JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT,"
                        + "isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,scalarConstructors=DEFAULT)";
        assertEquals(expectedNoOverrides,
                JsonAutoDetect.Value.noOverrides().toString());
    }
}
