package org.apache.commons.lang3.math;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testEnforceExceptions extends AbstractLangTest {

    private static final String EMPTY_ARRAY_MESSAGE = "IllegalArgumentException expected for empty input";
    private static final String NULL_ARRAY_MESSAGE = "IllegalArgumentException expected for null input";

    @Test
    void testEnforceExceptions() {
        assertFloatMinRejectsInvalidInput();
        assertFloatMaxRejectsInvalidInput();
        assertDoubleMinRejectsInvalidInput();
        assertDoubleMaxRejectsInvalidInput();
    }

    private void assertFloatMinRejectsInvalidInput() {
        assertNullPointerException(() -> IEEE754rUtils.min((float[]) null), NULL_ARRAY_MESSAGE);
        assertIllegalArgumentException(IEEE754rUtils::min, EMPTY_ARRAY_MESSAGE);
    }

    private void assertFloatMaxRejectsInvalidInput() {
        assertNullPointerException(() -> IEEE754rUtils.max((float[]) null), NULL_ARRAY_MESSAGE);
        assertIllegalArgumentException(IEEE754rUtils::max, EMPTY_ARRAY_MESSAGE);
    }

    private void assertDoubleMinRejectsInvalidInput() {
        assertNullPointerException(() -> IEEE754rUtils.min((double[]) null), NULL_ARRAY_MESSAGE);
        assertIllegalArgumentException(IEEE754rUtils::min, EMPTY_ARRAY_MESSAGE);
    }

    private void assertDoubleMaxRejectsInvalidInput() {
        assertNullPointerException(() -> IEEE754rUtils.max((double[]) null), NULL_ARRAY_MESSAGE);
        assertIllegalArgumentException(IEEE754rUtils::max, EMPTY_ARRAY_MESSAGE);
    }
}
