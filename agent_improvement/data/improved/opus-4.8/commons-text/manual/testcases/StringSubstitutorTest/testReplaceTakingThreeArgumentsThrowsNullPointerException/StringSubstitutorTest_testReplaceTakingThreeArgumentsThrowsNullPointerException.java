package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;

import org.junit.jupiter.api.Test;

/**
 * Tests the static {@link StringSubstitutor#replace(Object, Properties)} overload.
 */
public class StringSubstitutorTest_testReplaceTakingThreeArgumentsThrowsNullPointerException {

    /**
     * When the source object is {@code null}, {@code replace} ultimately calls
     * {@code source.toString()}, which throws a {@link NullPointerException}.
     */
    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        final Object nullSource = null;
        final Properties nullProperties = null;

        assertThrows(NullPointerException.class,
                () -> StringSubstitutor.replace(nullSource, nullProperties));
    }
}
