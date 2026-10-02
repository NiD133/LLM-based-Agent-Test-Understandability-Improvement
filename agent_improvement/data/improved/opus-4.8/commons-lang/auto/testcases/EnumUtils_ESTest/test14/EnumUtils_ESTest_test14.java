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
     * Verifies that {@link EnumUtils#isValidEnum(Class, String)} returns {@code false}
     * when the enum class is {@code null}, regardless of the supplied name.
     */
    @Test(timeout = 4000)
    public void isValidEnumReturnsFalseForNullEnumClass() throws Throwable {
        Class<Locale.FilteringMode> nullEnumClass = null;
        String enumName = "HaEk[w(YT.?P#4B";

        boolean valid = EnumUtils.isValidEnum(nullEnumClass, enumName);

        assertFalse(valid);
    }
}
