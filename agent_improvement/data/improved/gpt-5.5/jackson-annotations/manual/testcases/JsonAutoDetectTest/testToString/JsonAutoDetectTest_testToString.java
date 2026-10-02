package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonAutoDetectTest_testToString extends AnnotationTestUtil {

    private static final JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();
    private static final JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    private static final String DEFAULT_VISIBILITY_DESCRIPTION =
            "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,"
                    + "isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,"
                    + "scalarConstructors=NON_PRIVATE)";

    private static final String NO_OVERRIDES_DESCRIPTION =
            "JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT,"
                    + "isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,"
                    + "scalarConstructors=DEFAULT)";

    @Test
    public void testToString() {
        assertEquals(DEFAULT_VISIBILITY_DESCRIPTION, JsonAutoDetect.Value.defaultVisibility().toString());
        assertEquals(NO_OVERRIDES_DESCRIPTION, JsonAutoDetect.Value.noOverrides().toString());
    }
}
