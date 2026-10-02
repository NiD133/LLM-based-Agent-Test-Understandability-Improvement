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
public class EnumUtils_ESTest_test05 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        ToIntFunction<Locale.FilteringMode> toIntFunction0 = (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        Locale.FilteringMode locale_FilteringMode0 = Locale.FilteringMode.EXTENDED_FILTERING;
        Locale.FilteringMode locale_FilteringMode1 = EnumUtils.getFirstEnum((Class<Locale.FilteringMode>) null, 649, toIntFunction0, locale_FilteringMode0);
        assertSame(locale_FilteringMode1, locale_FilteringMode0);
    }
}
