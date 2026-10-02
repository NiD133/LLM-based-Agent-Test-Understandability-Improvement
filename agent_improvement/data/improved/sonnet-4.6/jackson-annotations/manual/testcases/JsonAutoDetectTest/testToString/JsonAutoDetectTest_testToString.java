package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testToString extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    // Expected toString() for the default visibility configuration:
    // fields=PUBLIC_ONLY, getters=PUBLIC_ONLY, isGetters=PUBLIC_ONLY,
    // setters=ANY, creators=PUBLIC_ONLY, scalarConstructors=NON_PRIVATE
    private static final String DEFAULT_VISIBILITY_STRING =
            "JsonAutoDetect.Value(" +
            "fields=PUBLIC_ONLY," +
            "getters=PUBLIC_ONLY," +
            "isGetters=PUBLIC_ONLY," +
            "setters=ANY," +
            "creators=PUBLIC_ONLY," +
            "scalarConstructors=NON_PRIVATE)";

    // Expected toString() for the no-overrides configuration:
    // all visibility settings are DEFAULT (meaning "inherit from parent context")
    private static final String NO_OVERRIDES_STRING =
            "JsonAutoDetect.Value(" +
            "fields=DEFAULT," +
            "getters=DEFAULT," +
            "isGetters=DEFAULT," +
            "setters=DEFAULT," +
            "creators=DEFAULT," +
            "scalarConstructors=DEFAULT)";

    @Test
    public void testToString() {
        assertEquals(DEFAULT_VISIBILITY_STRING, JsonAutoDetect.Value.defaultVisibility().toString());
        assertEquals(NO_OVERRIDES_STRING, JsonAutoDetect.Value.noOverrides().toString());
    }
}
