package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#generateBitVector(Class, Iterable)} rejects a
     * {@code null} enum class by throwing a {@link NullPointerException}, even when a
     * valid set of enum values is supplied.
     */
    @Test
    void testGenerateBitVector_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, EnumSet.of(Traffic.RED)));
    }
}
