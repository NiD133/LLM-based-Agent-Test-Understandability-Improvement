package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor#replace(Object, Properties)} throws
 * NullPointerException when both source and properties arguments are null,
 * because a null properties argument causes the method to call source.toString()
 * on the (also null) source object.
 */
public class StringSubstitutorTest_testReplaceTakingThreeArgumentsThrowsNullPointerException {

    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringSubstitutor.replace(null, (Properties) null));
    }
}
