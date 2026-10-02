package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetFirstEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        final Function<Traffic2, String> trafficLabel = Traffic2::getLabel;

        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***", trafficLabel, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**", trafficLabel, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*", trafficLabel, Traffic2.RED));

        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", trafficLabel, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple", trafficLabel, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE", trafficLabel, Traffic2.RED));

        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, trafficLabel, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, trafficLabel, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, trafficLabel, Traffic2.RED));

        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", trafficLabel, null));
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null, "PURPLE", trafficLabel, null));
    }

    private enum Traffic2 {
        RED("***red***"),
        AMBER("**Amber**"),
        GREEN("*grEEn*");

        private final String label;

        Traffic2(final String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }
}
