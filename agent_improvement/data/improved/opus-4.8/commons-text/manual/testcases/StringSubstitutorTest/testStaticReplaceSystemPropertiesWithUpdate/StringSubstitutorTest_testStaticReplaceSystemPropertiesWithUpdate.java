package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replaceSystemProperties(Object)} resolves
 * {@code ${...}} placeholders against the <em>current</em> system properties, so that
 * updating a property between calls is reflected in the next substitution.
 */
public class StringSubstitutorTest_testStaticReplaceSystemPropertiesWithUpdate {

    /** A system-property name that is unlikely to collide with real properties. */
    private static final String PROPERTY_NAME = "foo";

    /** The placeholder that {@code replaceSystemProperties} should resolve. */
    private static final String PLACEHOLDER = "${foo}";

    /**
     * Asserts equality of two character sequences, attaching their lengths to the
     * failure message to make length mismatches easier to diagnose.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests interpolation with system properties, including picking up a value that
     * changes after the first substitution.
     */
    @Test
    void testStaticReplaceSystemPropertiesWithUpdate() {
        System.setProperty(PROPERTY_NAME, "bar1");
        try {
            // The placeholder resolves to the property's initial value.
            assertEqualsCharSeq("bar1", StringSubstitutor.replaceSystemProperties(PLACEHOLDER));

            // After updating the property, the same placeholder resolves to the new value.
            System.setProperty(PROPERTY_NAME, "bar2");
            assertEqualsCharSeq("bar2", StringSubstitutor.replaceSystemProperties(PLACEHOLDER));
        } finally {
            // Avoid leaking the test property into other tests.
            System.getProperties().remove(PROPERTY_NAME);
        }
    }
}
