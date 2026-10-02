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

public class EnumUtilsTest_testGetFirstEnumToIntFunction extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetFirstEnumToIntFunction() {
        final ToIntFunction<Traffic2> f = Traffic2::getValue;
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 1, f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 2, f, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 3, f, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 4, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 5, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 6, f, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 0, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, -1, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 0, f, Traffic2.RED));
        assertNull(EnumUtils.getFirstEnum(Traffic2.class, 7, f, null));
        // Edge cases for 1st argument
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(null, 1, f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum((Class) String.class, 1, f, Traffic2.AMBER));
    }
}
