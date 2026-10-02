package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test14 extends EnumUtils_ESTest_scaffolding {

    /**
     * isValidEnum should return false when the enum class argument is null,
     * regardless of the enum name provided.
     */
    @Test(timeout = 4000)
    public void isValidEnum_returnsFalse_whenEnumClassIsNull() throws Throwable {
        boolean result = EnumUtils.isValidEnum((Class<Locale.FilteringMode>) null, "HaEk[w(YT.?P#4B");
        assertFalse(result);
    }
}
