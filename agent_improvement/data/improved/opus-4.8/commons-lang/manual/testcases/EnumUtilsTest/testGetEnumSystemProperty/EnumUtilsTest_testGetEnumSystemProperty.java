package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnumSystemProperty(Class, String, Object)}.
 */
public class EnumUtilsTest_testGetEnumSystemProperty extends AbstractLangTest {

    @Test
    void testGetEnumSystemProperty() {
        // Use this test class' name as the system property key, and point it at "RED".
        final String propertyKey = getClass().getName();
        System.setProperty(propertyKey, Traffic.RED.toString());
        try {
            // The property holds "RED", so the matching enum constant is resolved (default ignored).
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, propertyKey, null));

            // Unknown property key "?" -> property is missing, so fall back to the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, "?", Traffic.RED));

            // Null enum class -> cannot resolve, so fall back to the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, Traffic.RED));

            // Null enum class (with a key) -> cannot resolve, so fall back to the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, "?", Traffic.RED));

            // Null property key -> no value to look up, so fall back to the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, Traffic.RED));
        } finally {
            // Always clean up the system property so other tests are unaffected.
            System.getProperties().remove(propertyKey);
        }
    }
}
