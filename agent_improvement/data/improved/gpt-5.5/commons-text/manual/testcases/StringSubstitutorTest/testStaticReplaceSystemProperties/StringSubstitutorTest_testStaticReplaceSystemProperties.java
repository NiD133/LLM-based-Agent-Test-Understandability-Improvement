package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplaceSystemProperties {

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests interpolation with system properties.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final String template = "Hi ${user.name}, you are working with ${os.name}, your home directory is ${user.home}.";

        final TextStringBuilder expected = new TextStringBuilder();
        expected.append("Hi ").append(SystemProperties.getUserName());
        expected.append(", you are working with ");
        expected.append(SystemProperties.getOsName());
        expected.append(", your home directory is ");
        expected.append(SystemProperties.getUserHome()).append('.');

        assertEqualsCharSeq(expected.toString(), StringSubstitutor.replaceSystemProperties(template));
    }
}
