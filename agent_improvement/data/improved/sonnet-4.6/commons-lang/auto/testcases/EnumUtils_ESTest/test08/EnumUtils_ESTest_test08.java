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
public class EnumUtils_ESTest_test08 extends EnumUtils_ESTest_scaffolding {

    // Tests that getFirstEnum returns the default when no enum value matches the target int,
    // and that generateBitVectors correctly deduplicates repeated enum values via EnumSet.
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;

        // Mock always returns 0, so searching for -1 will never match any enum constant.
        ToIntFunction<Locale.FilteringMode> toIntFunction = (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(toIntFunction).applyAsInt(any(java.util.Locale.FilteringMode.class));

        Locale.FilteringMode defaultMode = Locale.FilteringMode.AUTOSELECT_FILTERING;

        // No enum constant maps to -1, so getFirstEnum returns the defaultMode (AUTOSELECT_FILTERING).
        Locale.FilteringMode noMatchResult = EnumUtils.getFirstEnum(filteringModeClass, (-1), toIntFunction, defaultMode);

        // Build an array of 9 FilteringMode values, all effectively AUTOSELECT_FILTERING
        // (both defaultMode and noMatchResult refer to the same constant).
        Locale.FilteringMode[] filteringModes = new Locale.FilteringMode[9];
        filteringModes[0] = defaultMode;
        filteringModes[1] = defaultMode;
        filteringModes[2] = noMatchResult;
        filteringModes[3] = defaultMode;
        filteringModes[4] = defaultMode;
        filteringModes[5] = defaultMode;
        filteringModes[6] = noMatchResult;
        filteringModes[7] = noMatchResult;
        filteringModes[8] = noMatchResult;

        // generateBitVectors deduplicates via EnumSet; only AUTOSELECT_FILTERING (ordinal 0)
        // is present, so the result is a single-element array with bit 0 set: {1L}.
        long[] bitVectors = EnumUtils.generateBitVectors(filteringModeClass, filteringModes);
        assertArrayEquals(new long[] { 1L }, bitVectors);
        assertEquals(1, bitVectors.length);
    }
}
