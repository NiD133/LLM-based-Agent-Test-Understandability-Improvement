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

    /**
     * Verifies that getFirstEnum returns the first enum constant when the
     * ToIntFunction matches the target value for all constants in the enum.
     *
     * Locale.FilteringMode constants in declaration order:
     *   AUTOSELECT_FILTERING, EXTENDED_FILTERING, IGNORE_EXTENDED_RANGES,
     *   MAP_EXTENDED_RANGES, REJECT_EXTENDED_RANGES
     *
     * The mock always returns 3 (== targetValue), so the first constant
     * AUTOSELECT_FILTERING is the first match and must be returned.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        int targetValue = 3;

        // Mock always returns targetValue regardless of which FilteringMode is passed
        ToIntFunction<Locale.FilteringMode> alwaysReturnsTarget =
            (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(targetValue).when(alwaysReturnsTarget).applyAsInt(any(java.util.Locale.FilteringMode.class));

        Locale.FilteringMode defaultEnum = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        // Because the mock matches every constant, the first one (AUTOSELECT_FILTERING) is returned
        Locale.FilteringMode result = EnumUtils.getFirstEnum(filteringModeClass, targetValue, alwaysReturnsTarget, defaultEnum);

        assertEquals(Locale.FilteringMode.AUTOSELECT_FILTERING, result);
    }
}
