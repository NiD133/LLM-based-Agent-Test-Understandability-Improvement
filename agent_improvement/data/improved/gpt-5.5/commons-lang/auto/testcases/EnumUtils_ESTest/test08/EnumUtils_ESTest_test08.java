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

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        ToIntFunction<Locale.FilteringMode> valueExtractor =
                (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(valueExtractor).applyAsInt(any(java.util.Locale.FilteringMode.class));

        Locale.FilteringMode defaultFilteringMode = Locale.FilteringMode.AUTOSELECT_FILTERING;
        Locale.FilteringMode matchedFilteringMode =
                EnumUtils.getFirstEnum(filteringModeClass, (-1), valueExtractor, defaultFilteringMode);

        Locale.FilteringMode[] filteringModes = new Locale.FilteringMode[] {
            defaultFilteringMode,
            defaultFilteringMode,
            matchedFilteringMode,
            defaultFilteringMode,
            defaultFilteringMode,
            defaultFilteringMode,
            matchedFilteringMode,
            matchedFilteringMode,
            matchedFilteringMode
        };

        long[] bitVectors = EnumUtils.generateBitVectors(filteringModeClass, filteringModes);

        assertArrayEquals(new long[] { 1L }, bitVectors);
        assertEquals(1, bitVectors.length);
    }
}
