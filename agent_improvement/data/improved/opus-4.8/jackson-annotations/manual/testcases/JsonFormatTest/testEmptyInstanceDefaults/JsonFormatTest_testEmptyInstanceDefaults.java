package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the shared "empty" {@link JsonFormat.Value} instance,
 * obtained from {@link JsonFormat.Value#empty()}, carries no explicit
 * configuration: every per-feature override is unset and none of the
 * optional settings (locale, pattern, shape, time zone, leniency, radix)
 * are present.
 */
public class JsonFormatTest_testEmptyInstanceDefaults extends AnnotationTestUtil {

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value emptyFormat = JsonFormat.Value.empty();

        // No individual Feature should have an explicit enabled/disabled override.
        for (Feature feature : Feature.values()) {
            assertNull(emptyFormat.getFeature(feature),
                    "Feature " + feature + " should have no override on an empty Value");
        }

        // None of the optional formatting settings should be configured.
        assertFalse(emptyFormat.hasLocale(), "empty Value must not declare a locale");
        assertFalse(emptyFormat.hasPattern(), "empty Value must not declare a pattern");
        assertFalse(emptyFormat.hasShape(), "empty Value must not declare a shape");
        assertFalse(emptyFormat.hasTimeZone(), "empty Value must not declare a time zone");
        assertFalse(emptyFormat.hasLenient(), "empty Value must not declare leniency");
        assertFalse(emptyFormat.hasNonDefaultRadix(), "empty Value must use the default radix");

        // With leniency unset, isLenient() resolves to false.
        assertFalse(emptyFormat.isLenient(), "empty Value must not report itself as lenient");
    }
}
