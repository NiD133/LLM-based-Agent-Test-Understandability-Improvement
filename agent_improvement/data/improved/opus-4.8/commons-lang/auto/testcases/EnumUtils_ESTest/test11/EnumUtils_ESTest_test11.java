package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.EnumSet;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test11 extends EnumUtils_ESTest_scaffolding {

    /**
     * processBitVector decodes a long bit vector back into the matching enum constants:
     * bit N (counting from the least significant bit) selects the enum constant with ordinal N.
     *
     * Locale.FilteringMode has 5 constants, so only bits 0-4 can map to a constant; any
     * higher bits in the vector are ignored. The value 2800 (binary 101011110000) only has
     * one of its set bits in the 0-4 range (bit 4), so exactly one enum constant is decoded.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        long bitVector = 2800L;

        EnumSet<Locale.FilteringMode> decodedConstants = EnumUtils.processBitVector(enumClass, bitVector);

        assertEquals(1, decodedConstants.size());
    }
}
