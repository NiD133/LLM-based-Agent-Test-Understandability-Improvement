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
public class EnumUtils_ESTest_test01 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void processBitVectorsWithEmptyBitVectorArrayReturnsEmptyEnumSet() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        long[] emptyBitVectors = new long[0];
        EnumSet<Locale.FilteringMode> result = EnumUtils.processBitVectors(filteringModeClass, emptyBitVectors);
        assertEquals(0, result.size());
    }
}
