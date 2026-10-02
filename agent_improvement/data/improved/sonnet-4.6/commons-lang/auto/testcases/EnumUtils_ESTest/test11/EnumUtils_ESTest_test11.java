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

    @Test(timeout = 4000)
    public void test11_processBitVector_returnsOnlyConstantsWhoseBitIsSet() throws Throwable {
        // Locale.FilteringMode has 5 constants (ordinals 0-4).
        // 2800L sets bits 4, 5, 6, 7, 9 and 11; only bit 4 (REJECT_EXTENDED_RANGES) is a valid ordinal.
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        EnumSet<Locale.FilteringMode> result = EnumUtils.processBitVector(filteringModeClass, 2800L);
        assertEquals(1, result.size());
    }
}
