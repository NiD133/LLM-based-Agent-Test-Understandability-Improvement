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
        Class<Locale.FilteringMode> class0 = Locale.FilteringMode.class;
        ToIntFunction<Locale.FilteringMode> toIntFunction0 = (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(toIntFunction0).applyAsInt(any(java.util.Locale.FilteringMode.class));
        Locale.FilteringMode locale_FilteringMode0 = Locale.FilteringMode.AUTOSELECT_FILTERING;
        Locale.FilteringMode locale_FilteringMode1 = EnumUtils.getFirstEnum(class0, (-1), toIntFunction0, locale_FilteringMode0);
        Locale.FilteringMode[] locale_FilteringModeArray0 = new Locale.FilteringMode[9];
        locale_FilteringModeArray0[0] = locale_FilteringMode0;
        locale_FilteringModeArray0[1] = locale_FilteringMode0;
        locale_FilteringModeArray0[2] = locale_FilteringMode1;
        locale_FilteringModeArray0[3] = locale_FilteringMode0;
        locale_FilteringModeArray0[4] = locale_FilteringMode0;
        locale_FilteringModeArray0[5] = locale_FilteringMode0;
        locale_FilteringModeArray0[6] = locale_FilteringMode1;
        locale_FilteringModeArray0[7] = locale_FilteringMode1;
        locale_FilteringModeArray0[8] = locale_FilteringMode1;
        long[] longArray0 = EnumUtils.generateBitVectors(class0, locale_FilteringModeArray0);
        assertArrayEquals(new long[] { 1L }, longArray0);
        assertEquals(1, longArray0.length);
    }
}
