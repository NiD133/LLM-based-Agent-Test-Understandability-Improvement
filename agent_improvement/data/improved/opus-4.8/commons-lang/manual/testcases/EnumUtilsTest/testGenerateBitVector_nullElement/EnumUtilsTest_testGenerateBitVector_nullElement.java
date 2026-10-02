package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link EnumUtils#generateBitVector(Class, Iterable)} rejects a
 * collection of enum values that contains a {@code null} element.
 */
public class EnumUtilsTest_testGenerateBitVector_nullElement extends AbstractLangTest {

    @Test
    void testGenerateBitVector_nullElement() {
        // A null entry among the enum values must trigger a NullPointerException.
        assertNullPointerException(
                () -> EnumUtils.generateBitVector(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }
}
