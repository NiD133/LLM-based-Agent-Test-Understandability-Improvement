package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test00 extends EnumUtils_ESTest_scaffolding {

    /**
     * generateBitVector sets the bit at each value's ordinal position.
     * Locale.FilteringMode.IGNORE_EXTENDED_RANGES has ordinal 2, so the
     * resulting bit vector is 1L << 2 == 4.
     */
    @Test(timeout = 4000)
    public void testGenerateBitVectorSetsBitAtValueOrdinal() throws Throwable {
        Locale.FilteringMode[] selectedValues = { Locale.FilteringMode.IGNORE_EXTENDED_RANGES };

        long bitVector = EnumUtils.generateBitVector(Locale.FilteringMode.class, selectedValues);

        assertEquals(4L, bitVector);
    }
}
