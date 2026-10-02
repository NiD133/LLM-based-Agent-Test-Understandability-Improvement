package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testGetEnum() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));

        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }
}
