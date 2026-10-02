package org.apache.commons.lang3.math;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link IEEE754rUtils#min} and {@link IEEE754rUtils#max} reject
 * invalid array arguments:
 * <ul>
 *   <li>a {@code null} array must raise a {@link NullPointerException};</li>
 *   <li>an empty array (no varargs supplied) must raise an
 *       {@link IllegalArgumentException}.</li>
 * </ul>
 * Both the {@code float[]} and {@code double[]} overloads are checked.
 */
public class IEEE754rUtilsTest_testEnforceExceptions extends AbstractLangTest {

    @Test
    void testEnforceExceptions() {
        // float[] overloads
        assertNullPointerException(() -> IEEE754rUtils.min((float[]) null),
            "NullPointerException expected for null float array");
        assertIllegalArgumentException(IEEE754rUtils::min,
            "IllegalArgumentException expected for empty float array");
        assertNullPointerException(() -> IEEE754rUtils.max((float[]) null),
            "NullPointerException expected for null float array");
        assertIllegalArgumentException(IEEE754rUtils::max,
            "IllegalArgumentException expected for empty float array");

        // double[] overloads
        assertNullPointerException(() -> IEEE754rUtils.min((double[]) null),
            "NullPointerException expected for null double array");
        assertIllegalArgumentException(IEEE754rUtils::min,
            "IllegalArgumentException expected for empty double array");
        assertNullPointerException(() -> IEEE754rUtils.max((double[]) null),
            "NullPointerException expected for null double array");
        assertIllegalArgumentException(IEEE754rUtils::max,
            "IllegalArgumentException expected for empty double array");
    }
}
