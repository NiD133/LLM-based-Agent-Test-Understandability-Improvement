package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceTakingThreeArgumentsThrowsNullPointerException {

    /**
     * When source is null and valueProperties is null, the static replace method
     * attempts source.toString() (because valueProperties == null skips the map path),
     * which throws NullPointerException because source itself is null.
     */
    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StrSubstitutor.replace(null, (Properties) null));
    }
}
