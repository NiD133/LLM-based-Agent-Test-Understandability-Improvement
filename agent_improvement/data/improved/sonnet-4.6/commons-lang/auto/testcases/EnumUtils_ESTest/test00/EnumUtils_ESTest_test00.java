package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test00 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // IGNORE_EXTENDED_RANGES has ordinal 2, so its bit vector is 1L << 2 = 4L
        long bitVector = EnumUtils.generateBitVector(Locale.FilteringMode.class, Locale.FilteringMode.IGNORE_EXTENDED_RANGES);
        assertEquals(4L, bitVector);
    }
}
