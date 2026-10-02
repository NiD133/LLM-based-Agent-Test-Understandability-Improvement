package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Locale;
import java.util.function.ToIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test05 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // When enumClass is null, getFirstEnum should return the defaultEnum unchanged
        ToIntFunction<Locale.FilteringMode> valueExtractor =
            (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        Locale.FilteringMode defaultEnum = Locale.FilteringMode.EXTENDED_FILTERING;

        Locale.FilteringMode result = EnumUtils.getFirstEnum(
            (Class<Locale.FilteringMode>) null, 649, valueExtractor, defaultEnum);

        assertSame(defaultEnum, result);
    }
}
