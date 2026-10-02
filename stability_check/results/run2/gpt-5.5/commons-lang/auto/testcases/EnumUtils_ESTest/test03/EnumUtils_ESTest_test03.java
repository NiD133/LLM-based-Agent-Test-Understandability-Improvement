package org.apache.commons.lang3;

import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Locale;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test03 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final Class<Locale.FilteringMode> filteringModeEnum = Locale.FilteringMode.class;

        final boolean isNullEnumNameValid = EnumUtils.isValidEnumIgnoreCase(filteringModeEnum, (String) null);

        assertFalse(isNullEnumNameValid);
    }
}
