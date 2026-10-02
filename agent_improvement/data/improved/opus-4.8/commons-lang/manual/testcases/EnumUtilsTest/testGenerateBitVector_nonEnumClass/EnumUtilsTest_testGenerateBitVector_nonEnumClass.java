package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClass extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVector(Class, Iterable)} only accepts enum classes.
     * Passing a non-enum class (here {@link Object}) must be rejected with an
     * {@link IllegalArgumentException}, regardless of the supplied values.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    void testGenerateBitVector_nonEnumClass() {
        final Class nonEnumClass = Object.class;
        final List values = new ArrayList();

        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass, values));
    }
}
