package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplaceSystemPropertiesWithUpdate {

    private static final String PROPERTY_NAME = "foo";
    private static final String PROPERTY_TEMPLATE = "${foo}";

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests interpolation with system properties.
     */
    @Test
    void testStaticReplaceSystemPropertiesWithUpdate() {
        System.setProperty(PROPERTY_NAME, "bar1");
        try {
            assertEqualsCharSeq("bar1", StringSubstitutor.replaceSystemProperties(PROPERTY_TEMPLATE));
            System.setProperty(PROPERTY_NAME, "bar2");
            assertEqualsCharSeq("bar2", StringSubstitutor.replaceSystemProperties(PROPERTY_TEMPLATE));
        } finally {
            System.getProperties().remove(PROPERTY_NAME);
        }
    }
}
