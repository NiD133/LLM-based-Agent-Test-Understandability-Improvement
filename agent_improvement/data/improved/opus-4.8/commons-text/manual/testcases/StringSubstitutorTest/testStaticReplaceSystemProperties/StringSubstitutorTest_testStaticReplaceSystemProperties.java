package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replaceSystemProperties(Object)}.
 */
public class StringSubstitutorTest_testStaticReplaceSystemProperties {

    /**
     * Asserts equality of two char sequences, reporting their lengths on failure
     * to make length mismatches easy to diagnose.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Verifies that {@code ${...}} placeholders referencing system properties are
     * interpolated with the current JVM's system property values.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final String template = "Hi ${user.name}, you are working with ${os.name}, your home directory is ${user.home}.";
        final String expected = "Hi " + SystemProperties.getUserName()
                + ", you are working with " + SystemProperties.getOsName()
                + ", your home directory is " + SystemProperties.getUserHome() + ".";

        assertEqualsCharSeq(expected, StringSubstitutor.replaceSystemProperties(template));
    }
}
