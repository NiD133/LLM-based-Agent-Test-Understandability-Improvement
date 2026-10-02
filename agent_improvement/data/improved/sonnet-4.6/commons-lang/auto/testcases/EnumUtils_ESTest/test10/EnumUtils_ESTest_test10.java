package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.function.ToIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test10 extends EnumUtils_ESTest_scaffolding {

    /**
     * When the system property key is an empty string (no such property exists),
     * getEnumSystemProperty should return the provided default enum value unchanged.
     */
    @Test(timeout = 4000)
    public void test_getEnumSystemProperty_returnsDefault_whenPropertyKeyIsEmpty() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        Locale.FilteringMode defaultValue = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        Locale.FilteringMode result = EnumUtils.getEnumSystemProperty(enumClass, "", defaultValue);

        assertSame(defaultValue, result);
    }
}
