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

    /**
     * A null enum name is never a valid value for any enum, so
     * {@link EnumUtils#isValidEnumIgnoreCase(Class, String)} should return false.
     */
    @Test(timeout = 4000)
    public void isValidEnumIgnoreCase_withNullName_returnsFalse() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        String nullEnumName = null;

        boolean isValid = EnumUtils.isValidEnumIgnoreCase(enumClass, nullEnumName);

        assertFalse(isValid);
    }
}
