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

public class EnumUtilsTest_testGetFirstEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        final Function<Traffic2, String> f = Traffic2::getLabel;
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***", f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**", f, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*", f, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple", f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE", f, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.RED));
        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", f, null));
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null, "PURPLE", f, null));
    }
}
