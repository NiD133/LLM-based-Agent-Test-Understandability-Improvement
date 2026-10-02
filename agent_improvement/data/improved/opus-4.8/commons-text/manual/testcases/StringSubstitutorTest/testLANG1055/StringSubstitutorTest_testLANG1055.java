package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for LANG-1055: {@link StringSubstitutor#replaceSystemProperties(Object)}
 * does not work consistently.
 *
 * <p>The fix guarantees that {@code replaceSystemProperties} behaves exactly like
 * {@link StringSubstitutor#replace(Object, java.util.Properties)} when the supplied
 * properties are the current {@link System#getProperties()}.</p>
 */
public class StringSubstitutorTest_testLANG1055 {

    @Test
    void testLANG1055() {
        // Given a system property that the template will reference.
        System.setProperty("test_key", "test_value");
        final String template = "test_key=${test_key}";

        // When substituting via the generic Properties-based call and via the
        // dedicated system-properties convenience method.
        final String expected = StringSubstitutor.replace(template, System.getProperties());
        final String actual = StringSubstitutor.replaceSystemProperties(template);

        // Then both approaches must yield the same resolved string.
        assertEquals(expected, actual);
    }
}
