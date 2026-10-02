package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceTakingThreeArgumentsThrowsNullPointerException {

    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringSubstitutor.replace(null, (Properties) null));
    }
}
