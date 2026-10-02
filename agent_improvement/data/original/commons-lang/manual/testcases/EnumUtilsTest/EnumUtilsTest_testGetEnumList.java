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

public class EnumUtilsTest_testGetEnumList extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetEnumList() {
        final List<Traffic> test = EnumUtils.getEnumList(Traffic.class);
        assertEquals(3, test.size());
        assertEquals(Traffic.RED, test.get(0));
        assertEquals(Traffic.AMBER, test.get(1));
        assertEquals(Traffic.GREEN, test.get(2));
    }
}
