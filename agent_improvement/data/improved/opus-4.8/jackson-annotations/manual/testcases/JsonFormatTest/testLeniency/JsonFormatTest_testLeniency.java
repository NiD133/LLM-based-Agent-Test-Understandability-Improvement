package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies how {@link JsonFormat.Value} tracks its three-state "lenient" setting:
 * unset (null), explicitly lenient (TRUE), and explicitly strict (FALSE).
 *
 * The setting is three-valued, so the test checks each state through the full
 * accessor trio ({@code hasLenient}, {@code isLenient}, {@code getLenient}) and
 * confirms that equality reflects the underlying value.
 */
public class JsonFormatTest_testLeniency extends AnnotationTestUtil {

    @Test
    public void testLeniency() {
        // --- Default state: leniency is not configured at all ---
        JsonFormat.Value unset = JsonFormat.Value.empty();
        assertFalse(unset.hasLenient(), "empty Value should have no leniency setting");
        assertFalse(unset.isLenient(), "unset leniency should not count as lenient");
        assertNull(unset.getLenient(), "unset leniency should report null");

        // --- Explicitly lenient: withLenient(TRUE) ---
        JsonFormat.Value lenient = unset.withLenient(Boolean.TRUE);
        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());

        // A value equals itself but differs from the unset value (in both directions).
        assertTrue(lenient.equals(lenient));
        assertFalse(unset.equals(lenient));
        assertFalse(lenient.equals(unset));

        // Re-applying the same setting is a no-op and must reuse the same instance.
        assertSame(lenient, lenient.withLenient(Boolean.TRUE));

        // --- Explicitly strict: withLenient(FALSE) ---
        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);
        assertTrue(strict.hasLenient(), "FALSE is still an explicit setting");
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());

        // Strict equals itself but differs from both the unset and lenient values.
        assertTrue(strict.equals(strict));
        assertFalse(unset.equals(strict));
        assertFalse(strict.equals(unset));
        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));

        // --- Clearing the setting: withLenient(null) restores the unset state ---
        JsonFormat.Value cleared = lenient.withLenient(null);
        assertFalse(cleared.hasLenient());
        assertFalse(cleared.isLenient());
        assertNull(cleared.getLenient());

        // Cleared is equivalent to the original unset value, but not to lenient.
        assertTrue(unset.equals(cleared));
        assertTrue(cleared.equals(unset));
        assertFalse(lenient.equals(cleared));
        assertFalse(cleared.equals(lenient));
    }
}
