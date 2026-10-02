package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonFormat.Value#empty()} returns a Value instance
 * that has no properties configured — every field is absent/default.
 */
public class JsonFormatTest_testEmptyInstanceDefaults extends AnnotationTestUtil {

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value empty = JsonFormat.Value.empty();

        // Every Feature must be unset (null = "no override configured")
        for (Feature feature : Feature.values()) {
            assertNull(empty.getFeature(feature),
                    "Feature " + feature + " should be unset on an empty Value");
        }

        // Structural / formatting fields must all be absent
        assertFalse(empty.hasLocale(),           "empty Value should have no locale");
        assertFalse(empty.hasPattern(),          "empty Value should have no pattern");
        assertFalse(empty.hasShape(),            "empty Value should have no shape (defaults to ANY)");
        assertFalse(empty.hasTimeZone(),         "empty Value should have no timezone");

        // Leniency must be completely unset — neither true nor false
        assertFalse(empty.hasLenient(),          "empty Value should have no leniency setting");
        assertFalse(empty.isLenient(),           "empty Value should not be considered lenient");

        // Radix must remain at the sentinel DEFAULT_RADIX value
        assertFalse(empty.hasNonDefaultRadix(),  "empty Value should not have a non-default radix");
    }
}
