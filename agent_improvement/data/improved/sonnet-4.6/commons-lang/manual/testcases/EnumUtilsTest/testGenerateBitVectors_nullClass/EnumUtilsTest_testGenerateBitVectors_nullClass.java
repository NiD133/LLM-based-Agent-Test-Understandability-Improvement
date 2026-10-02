package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullClass extends AbstractLangTest {

    @Test
    void testGenerateBitVectors_nullClass() {
        // A null enumClass argument must trigger NullPointerException
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, EnumSet.of(Traffic.RED)));
    }
}
