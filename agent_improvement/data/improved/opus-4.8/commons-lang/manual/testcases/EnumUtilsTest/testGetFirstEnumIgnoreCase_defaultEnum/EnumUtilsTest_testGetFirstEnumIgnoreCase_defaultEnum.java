package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getFirstEnumIgnoreCase(Class, String, Function, Object)}.
 *
 * <p>The lookup matches a search string against each constant's {@code label}
 * (via the supplied accessor) ignoring case, and falls back to a default value
 * when nothing matches.</p>
 */
public class EnumUtilsTest_testGetFirstEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    /** Maps each Traffic2 constant to the label that the lookup compares against. */
    private final Function<Traffic2, String> byLabel = Traffic2::getLabel;

    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        // A label that matches (ignoring case) returns that constant, never the default.
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***", byLabel, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**", byLabel, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*", byLabel, Traffic2.RED));

        // A label that matches no constant returns the supplied default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", byLabel, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple", byLabel, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE", byLabel, Traffic2.RED));

        // A null search string short-circuits to the supplied default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, byLabel, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, byLabel, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, byLabel, Traffic2.RED));

        // No match plus a null default yields null.
        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", byLabel, null));
        // A null enum class also yields the (null) default.
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null, "PURPLE", byLabel, null));
    }
}
