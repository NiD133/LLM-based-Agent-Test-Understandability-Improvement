package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumSystemProperty extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGetEnumSystemProperty() {
        final String propertyKey = getClass().getName();
        final Traffic defaultTraffic = Traffic.RED;

        System.setProperty(propertyKey, Traffic.RED.toString());
        try {
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, propertyKey, null));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, "?", defaultTraffic));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, defaultTraffic));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, "?", defaultTraffic));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, defaultTraffic));
        } finally {
            System.getProperties().remove(propertyKey);
        }
    }
}
