package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test06 extends EnumUtils_ESTest_scaffolding {

    // getEnum returns the default when the name does not match any constant
    @Test(timeout = 4000)
    public void test_getEnum_withInvalidName_returnsDefaultEnum() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        Locale.FilteringMode defaultMode = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        Locale.FilteringMode result = EnumUtils.getEnum(filteringModeClass, "+", defaultMode);

        assertSame(defaultMode, result);
    }
}
