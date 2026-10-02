package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the behaviour of {@link JsonFormat.Features}, the helper that tracks
 * which {@link Feature}s have been explicitly enabled or disabled. Each feature
 * is effectively three-valued: enabled ({@code Boolean.TRUE}), disabled
 * ({@code Boolean.FALSE}), or unset ({@code null}).
 */
public class JsonFormatTest_testFeatures extends AnnotationTestUtil {

    // Two distinct features used throughout the test for readability.
    private static final Feature ACCEPT_SINGLE_VALUE = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
    private static final Feature WRITE_NANOSECONDS = Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

    @Test
    public void testFeatures() {
        // An empty Features object has no explicit setting for any feature.
        JsonFormat.Features noSettings = JsonFormat.Features.empty();

        // Enable one feature and disable another.
        JsonFormat.Features customSettings = noSettings
                .with(ACCEPT_SINGLE_VALUE)
                .without(WRITE_NANOSECONDS);

        // --- equals() contract ---
        assertTrue(noSettings.equals(noSettings));     // reflexive
        assertFalse(noSettings.equals(customSettings)); // different settings
        assertFalse(noSettings.equals(null));           // never equal to null
        assertFalse(noSettings.equals("foo"));          // never equal to other types

        // --- get() reflects the explicit settings (null means "unset") ---
        assertNull(noSettings.get(ACCEPT_SINGLE_VALUE));
        assertEquals(Boolean.TRUE, customSettings.get(ACCEPT_SINGLE_VALUE));
        assertNull(noSettings.get(WRITE_NANOSECONDS));
        assertEquals(Boolean.FALSE, customSettings.get(WRITE_NANOSECONDS));

        // --- withOverrides() applies another object's settings on top of this one ---
        JsonFormat.Features merged = noSettings.withOverrides(customSettings);
        assertEquals(Boolean.TRUE, merged.get(ACCEPT_SINGLE_VALUE));
        assertEquals(Boolean.FALSE, merged.get(WRITE_NANOSECONDS));

        // --- construct(enabled, disabled) sets the two groups directly ---
        // Here the roles are swapped compared to customSettings above:
        // WRITE_NANOSECONDS is enabled and ACCEPT_SINGLE_VALUE is disabled.
        JsonFormat.Features swapped = JsonFormat.Features.construct(
                new Feature[] { WRITE_NANOSECONDS },   // enabled
                new Feature[] { ACCEPT_SINGLE_VALUE }); // disabled
        assertEquals(Boolean.FALSE, swapped.get(ACCEPT_SINGLE_VALUE));
        assertEquals(Boolean.TRUE, swapped.get(WRITE_NANOSECONDS));
    }
}
