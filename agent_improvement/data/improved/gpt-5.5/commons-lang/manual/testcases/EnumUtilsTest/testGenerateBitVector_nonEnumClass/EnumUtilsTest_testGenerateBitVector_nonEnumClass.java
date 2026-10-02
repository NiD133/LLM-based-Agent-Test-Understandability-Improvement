package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClass extends AbstractLangTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGenerateBitVector_nonEnumClass() {
        final Class nonEnumClass = Object.class;
        final List emptyValues = new ArrayList();

        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass, emptyValues));
    }
}
