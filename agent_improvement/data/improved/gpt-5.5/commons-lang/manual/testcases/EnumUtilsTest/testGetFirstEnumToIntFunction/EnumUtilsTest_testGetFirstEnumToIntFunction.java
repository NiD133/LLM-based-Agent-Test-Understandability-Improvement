package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetFirstEnumToIntFunction extends AbstractLangTest {

    private enum Traffic2 {
        RED(1),
        AMBER(2),
        GREEN(3);

        private final int value;

        Traffic2(final int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    private <E extends Enum<E>> void assertFirstEnum(final E expected, final Class<E> enumClass, final int lookupValue,
            final ToIntFunction<E> toIntFunction, final E defaultValue) {
        assertEquals(expected, EnumUtils.getFirstEnum(enumClass, lookupValue, toIntFunction, defaultValue));
    }

    @Test
    void testGetFirstEnumToIntFunction() {
        final ToIntFunction<Traffic2> f = Traffic2::getValue;
        assertFirstEnum(Traffic2.RED, Traffic2.class, 1, f, Traffic2.AMBER);
        assertFirstEnum(Traffic2.AMBER, Traffic2.class, 2, f, Traffic2.GREEN);
        assertFirstEnum(Traffic2.GREEN, Traffic2.class, 3, f, Traffic2.RED);
        assertFirstEnum(Traffic2.AMBER, Traffic2.class, 4, f, Traffic2.AMBER);
        assertFirstEnum(Traffic2.GREEN, Traffic2.class, 5, f, Traffic2.GREEN);
        assertFirstEnum(Traffic2.RED, Traffic2.class, 6, f, Traffic2.RED);

        assertFirstEnum(Traffic2.AMBER, Traffic2.class, 0, f, Traffic2.AMBER);
        assertFirstEnum(Traffic2.GREEN, Traffic2.class, -1, f, Traffic2.GREEN);
        assertFirstEnum(Traffic2.RED, Traffic2.class, 0, f, Traffic2.RED);
        assertNull(EnumUtils.getFirstEnum(Traffic2.class, 7, f, null));

        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(null, 1, f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum((Class) String.class, 1, f, Traffic2.AMBER));
    }
}
