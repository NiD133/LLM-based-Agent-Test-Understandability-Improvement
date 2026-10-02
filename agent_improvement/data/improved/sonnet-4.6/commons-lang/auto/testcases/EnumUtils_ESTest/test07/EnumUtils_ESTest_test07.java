package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test07 extends EnumUtils_ESTest_scaffolding {

    /**
     * generateBitVectors with a single enum value (AUTOSELECT_FILTERING, ordinal 0)
     * should return a one-element long array containing 1L (bit 0 set).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;

        List<Locale.FilteringMode> selectedModes = new ArrayList<>();
        selectedModes.add(Locale.FilteringMode.AUTOSELECT_FILTERING);

        long[] bitVectors = EnumUtils.generateBitVectors(
                filteringModeClass, (Iterable<? extends Locale.FilteringMode>) selectedModes);

        assertArrayEquals(new long[] { 1L }, bitVectors);
        assertEquals(1, bitVectors.length);
    }
}
