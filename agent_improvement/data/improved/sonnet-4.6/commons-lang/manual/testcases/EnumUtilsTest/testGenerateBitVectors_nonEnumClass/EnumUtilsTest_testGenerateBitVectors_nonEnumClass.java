package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClass extends AbstractLangTest {

    /**
     * Verifies that generateBitVectors throws IllegalArgumentException when the
     * supplied class is not an enum type. Object.class is used as a representative
     * non-enum class; raw types are required to bypass the compile-time enum bound.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testGenerateBitVectors_nonEnumClass() {
        final Class nonEnumClass = Object.class;
        final List emptyValues = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumClass, emptyValues));
    }
}
