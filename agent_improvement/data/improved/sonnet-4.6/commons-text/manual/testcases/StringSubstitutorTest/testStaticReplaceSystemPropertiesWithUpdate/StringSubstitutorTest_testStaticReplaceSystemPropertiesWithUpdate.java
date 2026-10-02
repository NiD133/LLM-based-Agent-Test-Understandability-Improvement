package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplaceSystemPropertiesWithUpdate {

    /**
     * Asserts two CharSequences are equal, reporting their lengths on mismatch to aid diagnosis.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests that replaceSystemProperties reflects live system-property updates:
     * a second call after changing the property value must return the new value.
     */
    @Test
    void testStaticReplaceSystemPropertiesWithUpdate() {
        System.setProperty("foo", "bar1");
        try {
            assertEqualsCharSeq("bar1", StringSubstitutor.replaceSystemProperties("${foo}"));
            System.setProperty("foo", "bar2");
            assertEqualsCharSeq("bar2", StringSubstitutor.replaceSystemProperties("${foo}"));
        } finally {
            System.getProperties().remove("foo");
        }
    }
}
