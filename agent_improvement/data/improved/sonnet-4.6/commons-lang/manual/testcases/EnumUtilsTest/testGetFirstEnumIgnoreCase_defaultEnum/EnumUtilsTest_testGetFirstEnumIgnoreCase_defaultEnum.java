package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getFirstEnumIgnoreCase(Class, String, Function, Enum)} with a custom
 * label function and an explicit default enum value.
 *
 * <p>{@code Traffic2} labels are decorated with asterisks (e.g. {@code "***Red***"}) so that
 * matching by enum name would fail; matching succeeds only via the label function, case-insensitively.
 */
public class EnumUtilsTest_testGetFirstEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    /** Maps each {@link Traffic2} constant to its decorated label string. */
    private static final Function<Traffic2, String> GET_LABEL = Traffic2::getLabel;

    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        // Exact label matches (case-insensitive): returns the matching constant, ignoring the default
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***",  GET_LABEL, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**",  GET_LABEL, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*",    GET_LABEL, Traffic2.RED));

        // No matching label: returns the supplied default enum constant
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE",     GET_LABEL, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple",     GET_LABEL, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE",     GET_LABEL, Traffic2.RED));

        // Null search value: returns the supplied default enum constant
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null,         GET_LABEL, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null,         GET_LABEL, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null,         GET_LABEL, Traffic2.RED));

        // Null default: returns null when no match is found or enumClass is null
        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", GET_LABEL, null));
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null,            "PURPLE", GET_LABEL, null));
    }
}
