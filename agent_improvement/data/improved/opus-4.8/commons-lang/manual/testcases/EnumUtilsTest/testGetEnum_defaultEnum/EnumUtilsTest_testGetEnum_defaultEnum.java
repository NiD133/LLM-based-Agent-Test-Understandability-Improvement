package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnum(Class, String, Enum)}, the three-argument overload
 * that returns a caller-supplied default instead of {@code null} when the lookup fails.
 */
public class EnumUtilsTest_testGetEnum_defaultEnum extends AbstractLangTest {

    @Test
    void testGetEnum_defaultEnum() {
        // When the name matches a constant, that constant is returned and the default is ignored.
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));

        // When the name does not match any constant, the supplied default is returned.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));

        // A null name is treated as "not found" and yields the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, null, Traffic.RED));

        // A null default is returned as-is when the name does not match.
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));

        // A null enum class is treated as "not found" and yields the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(null, "RED", Traffic.AMBER));
    }
}
