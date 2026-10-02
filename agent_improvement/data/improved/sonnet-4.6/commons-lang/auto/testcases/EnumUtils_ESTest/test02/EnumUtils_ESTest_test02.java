package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test02 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isValidEnumIgnoreCase_matchesLowercaseEnumName() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        boolean isValid = EnumUtils.isValidEnumIgnoreCase(filteringModeClass, "autoselect_filtering");
        assertTrue(isValid);
    }
}
