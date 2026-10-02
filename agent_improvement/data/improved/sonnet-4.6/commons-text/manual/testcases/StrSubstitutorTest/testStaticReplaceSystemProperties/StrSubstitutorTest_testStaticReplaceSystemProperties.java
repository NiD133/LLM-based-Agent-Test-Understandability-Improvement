package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.SystemProperties;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplaceSystemProperties {

    /**
     * Tests that replaceSystemProperties correctly interpolates ${user.name},
     * ${os.name}, and ${user.home} placeholders with their actual system property values.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        String template = "Hi ${user.name}, you are working with ${os.name}, your home directory is ${user.home}.";

        String expected = "Hi " + SystemProperties.getUserName()
                + ", you are working with " + SystemProperties.getOsName()
                + ", your home directory is " + SystemProperties.getUserHome() + ".";

        assertEquals(expected, StrSubstitutor.replaceSystemProperties(template));
    }
}
