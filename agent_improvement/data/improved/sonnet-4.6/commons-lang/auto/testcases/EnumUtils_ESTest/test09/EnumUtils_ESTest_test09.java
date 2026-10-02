package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test09 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09_generateBitVector_allFilteringModeValues_returns31() throws Throwable {
        // Locale.FilteringMode has 5 constants (ordinals 0-4), so all bits 0-4 set = 2^5 - 1 = 31
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        List<Locale.FilteringMode> allFilteringModes = EnumUtils.getEnumList(filteringModeClass);
        long bitVector = EnumUtils.generateBitVector(filteringModeClass, (Iterable<? extends Locale.FilteringMode>) allFilteringModes);
        assertEquals(31L, bitVector);
    }
}
