package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test10 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#getEnumSystemProperty} falls back to the supplied
     * default enum when the system property name cannot be resolved to a valid enum constant.
     * Here the property name is empty, so no matching enum is found and the default is returned.
     */
    @Test(timeout = 4000)
    public void getEnumSystemProperty_unresolvableProperty_returnsDefaultEnum() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        Locale.FilteringMode defaultMode = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        Locale.FilteringMode result =
                EnumUtils.getEnumSystemProperty(enumClass, "", defaultMode);

        assertSame(defaultMode, result);
    }
}
