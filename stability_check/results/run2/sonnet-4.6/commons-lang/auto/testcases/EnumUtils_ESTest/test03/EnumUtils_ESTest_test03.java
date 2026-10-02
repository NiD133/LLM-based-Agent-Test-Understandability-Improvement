package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test03 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isValidEnumIgnoreCase_returnsFalse_whenEnumNameIsNull() throws Throwable {
        boolean result = EnumUtils.isValidEnumIgnoreCase(Locale.FilteringMode.class, (String) null);
        assertFalse("isValidEnumIgnoreCase should return false when the enum name is null", result);
    }
}
