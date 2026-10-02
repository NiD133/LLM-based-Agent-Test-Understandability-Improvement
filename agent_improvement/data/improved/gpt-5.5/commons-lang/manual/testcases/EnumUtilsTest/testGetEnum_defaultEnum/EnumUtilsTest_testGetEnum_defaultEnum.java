package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_defaultEnum extends AbstractLangTest {

    @Test
    void testGetEnum_defaultEnum() {
        assertEnumIsReturned(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED", Traffic.AMBER));
        assertEnumIsReturned(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
        assertEnumIsReturned(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));

        assertDefaultEnumIsReturned(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
        assertDefaultEnumIsReturned(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
        assertDefaultEnumIsReturned(Traffic.RED, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));

        assertDefaultEnumIsReturned(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
        assertDefaultEnumIsReturned(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
        assertDefaultEnumIsReturned(Traffic.RED, EnumUtils.getEnum(Traffic.class, null, Traffic.RED));

        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(null, "RED", Traffic.AMBER));
    }

    private void assertEnumIsReturned(final Object expectedEnum, final Object actualEnum) {
        assertEquals(expectedEnum, actualEnum);
    }

    private void assertDefaultEnumIsReturned(final Object defaultEnum, final Object actualEnum) {
        assertEquals(defaultEnum, actualEnum);
    }

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }
}
