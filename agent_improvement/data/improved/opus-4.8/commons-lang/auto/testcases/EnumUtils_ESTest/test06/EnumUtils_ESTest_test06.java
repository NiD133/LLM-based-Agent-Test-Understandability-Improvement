package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test06 extends EnumUtils_ESTest_scaffolding {

    /**
     * When the requested name does not match any constant of the enum,
     * {@link EnumUtils#getEnum(Class, String, Enum)} should fall back to the
     * supplied default value rather than throwing.
     */
    @Test(timeout = 4000)
    public void getEnumReturnsDefaultWhenNameIsUnknown() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        Locale.FilteringMode defaultValue = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;
        String unknownName = "+";

        Locale.FilteringMode result = EnumUtils.getEnum(enumClass, unknownName, defaultValue);

        assertSame(defaultValue, result);
    }
}
