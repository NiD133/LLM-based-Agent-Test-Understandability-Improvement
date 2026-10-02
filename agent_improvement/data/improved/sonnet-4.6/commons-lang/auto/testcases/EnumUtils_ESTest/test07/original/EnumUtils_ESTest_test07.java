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
        Class<Locale.FilteringMode> class0 = Locale.FilteringMode.class;
        Vector<Locale.FilteringMode> vector0 = new Vector<Locale.FilteringMode>(0);
        Locale.FilteringMode locale_FilteringMode0 = Locale.FilteringMode.AUTOSELECT_FILTERING;
        vector0.add(locale_FilteringMode0);
        long[] longArray0 = EnumUtils.generateBitVectors(class0, (Iterable<? extends Locale.FilteringMode>) vector0);
        assertArrayEquals(new long[] { 1L }, longArray0);
        assertEquals(1, longArray0.length);
    }
}
