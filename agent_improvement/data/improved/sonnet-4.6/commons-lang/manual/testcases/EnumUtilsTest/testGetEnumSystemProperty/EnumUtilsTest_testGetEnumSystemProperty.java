package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumSystemProperty extends AbstractLangTest {

    // A key that is never set in system properties, so lookups return null
    private static final String ABSENT_PROPERTY_KEY = "?";

    @Test
    void testGetEnumSystemProperty() {
        // Use a class-scoped key to avoid clashing with properties set by other tests
        final String propertyKey = getClass().getName();
        System.setProperty(propertyKey, Traffic.RED.toString());
        try {
            // Property exists and its value matches a valid enum name → resolved enum is returned
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, propertyKey, null));

            // Property key is absent from system properties → default enum is returned
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, ABSENT_PROPERTY_KEY, Traffic.RED));

            // Both enum class and property key are null → default enum is returned
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, Traffic.RED));

            // Enum class is null (property key present but irrelevant) → default enum is returned
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, ABSENT_PROPERTY_KEY, Traffic.RED));

            // Property key is null → SystemProperties.getProperty(null) yields null → default enum is returned
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, Traffic.RED));
        } finally {
            System.getProperties().remove(propertyKey);
        }
    }
}
