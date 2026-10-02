package org.apache.commons.lang3;

import static org.junit.Assert.assertFalse;

import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test03 extends EnumUtils_ESTest_scaffolding {

    /**
     * A {@code null} enum name is never a valid enum constant, so
     * {@link EnumUtils#isValidEnumIgnoreCase} should return {@code false}
     * regardless of the enum class supplied.
     */
    @Test(timeout = 4000)
    public void isValidEnumIgnoreCase_returnsFalse_whenEnumNameIsNull() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        String nullEnumName = null;

        boolean isValid = EnumUtils.isValidEnumIgnoreCase(enumClass, nullEnumName);

        assertFalse("A null enum name must not be considered a valid enum constant", isValid);
    }
}
