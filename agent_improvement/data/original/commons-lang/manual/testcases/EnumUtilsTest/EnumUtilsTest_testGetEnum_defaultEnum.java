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
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, null, Traffic.RED));
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(null, "RED", Traffic.AMBER));
    }
}
