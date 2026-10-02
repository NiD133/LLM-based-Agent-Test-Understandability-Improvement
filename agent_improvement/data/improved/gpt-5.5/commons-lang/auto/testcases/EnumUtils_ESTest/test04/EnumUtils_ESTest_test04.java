package org.apache.commons.lang3;

import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.function.ToIntFunction;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test04 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getFirstEnumReturnsFirstEnumWithMatchingIntValue() throws Throwable {
        final Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        final ToIntFunction<Locale.FilteringMode> matchingValueFunction =
                (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(3).when(matchingValueFunction).applyAsInt(any(java.util.Locale.FilteringMode.class));
        final Locale.FilteringMode defaultFilteringMode = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        final Locale.FilteringMode actualFilteringMode =
                EnumUtils.getFirstEnum(filteringModeClass, 3, matchingValueFunction, defaultFilteringMode);

        assertEquals(Locale.FilteringMode.AUTOSELECT_FILTERING, actualFilteringMode);
    }
}
