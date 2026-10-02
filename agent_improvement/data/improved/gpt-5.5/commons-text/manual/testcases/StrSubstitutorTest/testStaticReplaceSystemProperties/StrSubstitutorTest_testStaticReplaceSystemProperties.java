package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.SystemProperties;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplaceSystemProperties {

    private static final String SYSTEM_PROPERTIES_TEMPLATE =
            "Hi ${user.name}, you are working with ${os.name}, your home directory is ${user.home}.";

    /**
     * Tests interpolation with system properties.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final StrBuilder expectedMessage = new StrBuilder();
        expectedMessage.append("Hi ").append(SystemProperties.getUserName());
        expectedMessage.append(", you are working with ");
        expectedMessage.append(SystemProperties.getOsName());
        expectedMessage.append(", your home directory is ");
        expectedMessage.append(SystemProperties.getUserHome()).append('.');

        assertEquals(
                expectedMessage.toString(),
                StrSubstitutor.replaceSystemProperties(SYSTEM_PROPERTIES_TEMPLATE));
    }
}
