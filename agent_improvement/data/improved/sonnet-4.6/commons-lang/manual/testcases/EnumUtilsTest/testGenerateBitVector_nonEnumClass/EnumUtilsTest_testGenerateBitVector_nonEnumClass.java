package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClass extends AbstractLangTest {

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClass() {
        // Object.class is not an enum type; generateBitVector must reject it with IllegalArgumentException
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        @SuppressWarnings("rawtypes")
        final List emptyValues = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass, emptyValues));
    }
}
