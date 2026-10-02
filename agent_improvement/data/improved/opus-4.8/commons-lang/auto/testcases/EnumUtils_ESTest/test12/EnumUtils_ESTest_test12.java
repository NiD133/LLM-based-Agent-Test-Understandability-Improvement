package org.apache.commons.lang3;

import static org.junit.Assert.assertFalse;

import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test12 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#isValidEnumIgnoreCase(Class, String)} returns
     * {@code false} when the enum class is {@code null}, regardless of the supplied name.
     * Per its contract, a null enum class is never a valid enum.
     */
    @Test(timeout = 4000)
    public void isValidEnumIgnoreCase_withNullEnumClass_returnsFalse() throws Throwable {
        final Class<Locale.FilteringMode> nullEnumClass = null;
        final String anyEnumName = "3qV_Tp24g8R8F5_u";

        boolean valid = EnumUtils.isValidEnumIgnoreCase(nullEnumClass, anyEnumName);

        assertFalse("A null enum class must never be reported as valid", valid);
    }
}
