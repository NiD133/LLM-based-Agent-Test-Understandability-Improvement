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
public class EnumUtils_ESTest_test04 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Class<Locale.FilteringMode> class0 = Locale.FilteringMode.class;
        ToIntFunction<Locale.FilteringMode> toIntFunction0 = (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(3).when(toIntFunction0).applyAsInt(any(java.util.Locale.FilteringMode.class));
        Locale.FilteringMode locale_FilteringMode0 = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;
        Locale.FilteringMode locale_FilteringMode1 = EnumUtils.getFirstEnum(class0, 3, toIntFunction0, locale_FilteringMode0);
        assertEquals(Locale.FilteringMode.AUTOSELECT_FILTERING, locale_FilteringMode1);
    }
}
