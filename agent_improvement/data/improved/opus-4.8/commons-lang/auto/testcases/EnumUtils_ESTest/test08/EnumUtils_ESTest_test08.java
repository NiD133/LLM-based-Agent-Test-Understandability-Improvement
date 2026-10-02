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
public class EnumUtils_ESTest_test08 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#generateBitVectors(Class, Enum[])} produces a
     * one-element bit vector when the only distinct enum value present is the first
     * constant (ordinal 0) of {@link Locale.FilteringMode}.
     *
     * <p>The {@link EnumUtils#getFirstEnum} call is used to obtain the fallback value:
     * because the mocked {@link ToIntFunction} always returns 0 while the lookup value
     * is -1, no constant matches and the method returns the supplied default
     * ({@code AUTOSELECT_FILTERING}). The resulting array therefore contains only that
     * single constant, which sits at ordinal 0, yielding the bit vector {@code {1L}}.</p>
     */
    @Test(timeout = 4000)
    public void generateBitVectors_withOnlyFirstConstant_returnsSingleLowBit() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;

        // A function that never matches the lookup value (always returns 0, never -1).
        ToIntFunction<Locale.FilteringMode> alwaysZeroFunction =
                (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(alwaysZeroFunction).applyAsInt(any(Locale.FilteringMode.class));

        Locale.FilteringMode defaultMode = Locale.FilteringMode.AUTOSELECT_FILTERING;

        // No constant maps to -1, so getFirstEnum falls back to the default value.
        Locale.FilteringMode firstEnum =
                EnumUtils.getFirstEnum(filteringModeClass, -1, alwaysZeroFunction, defaultMode);
        assertSame(defaultMode, firstEnum);

        // Every slot holds AUTOSELECT_FILTERING (ordinal 0), so the condensed set has one constant.
        Locale.FilteringMode[] values = new Locale.FilteringMode[9];
        values[0] = defaultMode;
        values[1] = defaultMode;
        values[2] = firstEnum;
        values[3] = defaultMode;
        values[4] = defaultMode;
        values[5] = defaultMode;
        values[6] = firstEnum;
        values[7] = firstEnum;
        values[8] = firstEnum;

        long[] bitVectors = EnumUtils.generateBitVectors(filteringModeClass, values);

        assertArrayEquals(new long[] { 1L }, bitVectors);
        assertEquals(1, bitVectors.length);
    }
}
