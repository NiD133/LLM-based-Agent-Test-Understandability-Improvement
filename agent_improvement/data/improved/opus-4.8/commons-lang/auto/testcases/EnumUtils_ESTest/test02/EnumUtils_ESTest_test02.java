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

    /**
     * isValidEnumIgnoreCase should accept an enum constant name that differs
     * only in case. The constant Locale.FilteringMode.AUTOSELECT_FILTERING is
     * matched here using its lower-case spelling "autoselect_filtering".
     */
    @Test(timeout = 4000)
    public void isValidEnumIgnoreCase_matchesNameDifferingOnlyInCase() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;

        boolean isValid = EnumUtils.isValidEnumIgnoreCase(enumClass, "autoselect_filtering");

        assertTrue(isValid);
    }
}
