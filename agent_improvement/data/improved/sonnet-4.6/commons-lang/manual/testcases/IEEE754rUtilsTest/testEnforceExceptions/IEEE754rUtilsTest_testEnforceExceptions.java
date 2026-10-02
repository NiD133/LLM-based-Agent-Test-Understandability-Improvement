package org.apache.commons.lang3.math;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testEnforceExceptions extends AbstractLangTest {

    @Nested
    class FloatArrayValidation {

        @Test
        void minThrowsNullPointerExceptionForNullArray() {
            assertNullPointerException(() -> IEEE754rUtils.min((float[]) null), "NullPointerException expected for null float array");
        }

        @Test
        void minThrowsIllegalArgumentExceptionForEmptyArray() {
            assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty float array");
        }

        @Test
        void maxThrowsNullPointerExceptionForNullArray() {
            assertNullPointerException(() -> IEEE754rUtils.max((float[]) null), "NullPointerException expected for null float array");
        }

        @Test
        void maxThrowsIllegalArgumentExceptionForEmptyArray() {
            assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty float array");
        }
    }

    @Nested
    class DoubleArrayValidation {

        @Test
        void minThrowsNullPointerExceptionForNullArray() {
            assertNullPointerException(() -> IEEE754rUtils.min((double[]) null), "NullPointerException expected for null double array");
        }

        @Test
        void minThrowsIllegalArgumentExceptionForEmptyArray() {
            assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty double array");
        }

        @Test
        void maxThrowsNullPointerExceptionForNullArray() {
            assertNullPointerException(() -> IEEE754rUtils.max((double[]) null), "NullPointerException expected for null double array");
        }

        @Test
        void maxThrowsIllegalArgumentExceptionForEmptyArray() {
            assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty double array");
        }
    }
}
