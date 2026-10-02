package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_defaultEnum extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetEnum_defaultEnum() {
        // When the name matches a valid enum constant, that constant is returned (default is ignored)
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "RED",   Traffic.AMBER), "Valid name 'RED' should return Traffic.RED");
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN), "Valid name 'AMBER' should return Traffic.AMBER");
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED),   "Valid name 'GREEN' should return Traffic.GREEN");

        // When the name does not match any constant, the supplied default is returned
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER), "Unknown name 'PURPLE' should fall back to default Traffic.AMBER");
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN), "Unknown name 'PURPLE' should fall back to default Traffic.GREEN");
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED),   "Unknown name 'PURPLE' should fall back to default Traffic.RED");

        // When the name is null, the supplied default is returned
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER), "Null name should fall back to default Traffic.AMBER");
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN), "Null name should fall back to default Traffic.GREEN");
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, null, Traffic.RED),   "Null name should fall back to default Traffic.RED");

        // When the default itself is null, null is returned for an unresolvable name
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null), "Unknown name with null default should return null");

        // When the enum class is null, the supplied default is returned regardless of the name
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(null, "RED", Traffic.AMBER), "Null enum class should fall back to default Traffic.AMBER");
    }
}
