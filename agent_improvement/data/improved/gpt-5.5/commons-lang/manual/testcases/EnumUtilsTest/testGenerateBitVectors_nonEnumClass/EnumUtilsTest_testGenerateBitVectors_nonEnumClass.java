package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClass extends AbstractLangTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGenerateBitVectors_nonEnumClass() {
        final Class nonEnumType = Object.class;
        final List selectedValues = new ArrayList();

        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumType, selectedValues));
    }
}
