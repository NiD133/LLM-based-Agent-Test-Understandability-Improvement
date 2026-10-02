package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.SystemProperties;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplaceSystemProperties {

    /**
     * Tests that replaceSystemProperties resolves ${user.name}, ${os.name}, and
     * ${user.home} variables from the current JVM system properties.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final String template = "Hi ${user.name}, you are working with ${os.name}, your home directory is ${user.home}.";

        final String expectedResult = "Hi " + SystemProperties.getUserName()
                + ", you are working with " + SystemProperties.getOsName()
                + ", your home directory is " + SystemProperties.getUserHome() + ".";

        assertEquals(expectedResult, StringSubstitutor.replaceSystemProperties(template));
    }
}
