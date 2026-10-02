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
public class EnumUtils_ESTest_test07 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final Class<Locale.FilteringMode> filteringModeClass = Locale.FilteringMode.class;
        final Vector<Locale.FilteringMode> selectedModes = new Vector<Locale.FilteringMode>(0);
        final Locale.FilteringMode autoSelectFiltering = Locale.FilteringMode.AUTOSELECT_FILTERING;

        selectedModes.add(autoSelectFiltering);

        final long[] bitVectors = EnumUtils.generateBitVectors(
                filteringModeClass,
                (Iterable<? extends Locale.FilteringMode>) selectedModes);

        assertArrayEquals(new long[] { 1L }, bitVectors);
        assertEquals(1, bitVectors.length);
    }
}
