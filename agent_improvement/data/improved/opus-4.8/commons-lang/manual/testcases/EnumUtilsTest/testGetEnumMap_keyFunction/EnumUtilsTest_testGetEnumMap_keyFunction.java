package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnumMap(Class, java.util.function.Function)}, the overload that
 * builds a map keyed by a caller-supplied function rather than by the enum name.
 */
public class EnumUtilsTest_testGetEnumMap_keyFunction extends AbstractLangTest {

    @Test
    void testGetEnumMap_keyFunction() {
        // Key every Month by its numeric id (Month.JAN -> 1, ..., Month.DEC -> 12).
        final Map<Integer, Month> actual = EnumUtils.getEnumMap(Month.class, Month::getId);

        // The resulting map must contain exactly one entry per Month, keyed by id.
        final Map<Integer, Month> expected = new HashMap<>();
        for (final Month month : Month.values()) {
            expected.put(month.getId(), month);
        }
        assertEquals(expected, actual, "getEnumMap not created correctly");
        assertEquals(12, actual.size());

        // Ids 1..12 are present and resolve to the matching Month constant.
        for (final Month month : Month.values()) {
            final int id = month.getId();
            assertTrue(actual.containsKey(id), () -> "missing id " + id);
            assertEquals(month, actual.get(id));
        }

        // Ids just outside the valid 1..12 range are absent.
        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(13));
    }
}
