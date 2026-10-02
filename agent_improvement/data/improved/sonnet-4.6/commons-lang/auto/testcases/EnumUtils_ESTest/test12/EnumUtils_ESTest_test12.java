package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test12 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isValidEnumIgnoreCase_returnsFalse_whenEnumClassIsNull() throws Throwable {
        boolean isValid = EnumUtils.isValidEnumIgnoreCase((Class<Locale.FilteringMode>) null, "3qV_Tp24g8R8F5_u");
        assertFalse(isValid);
    }
}
