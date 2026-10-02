package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test07 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#generateBitVectors(Class, Iterable)} encodes a single
     * enum constant as the bit at its ordinal position.
     *
     * <p>{@code Locale.FilteringMode} has 4 constants, so the result fits in one {@code long}.
     * {@code AUTOSELECT_FILTERING} is the first constant (ordinal 0), so the expected bit
     * vector is {@code 1L} (binary {@code ...0001}).</p>
     */
    @Test(timeout = 4000)
    public void generateBitVectorsForSingleConstantSetsOrdinalBit() throws Throwable {
        List<Locale.FilteringMode> selectedModes = new Vector<Locale.FilteringMode>();
        selectedModes.add(Locale.FilteringMode.AUTOSELECT_FILTERING);

        long[] bitVectors = EnumUtils.generateBitVectors(Locale.FilteringMode.class, selectedModes);

        assertArrayEquals(new long[] { 1L }, bitVectors);
    }
}
