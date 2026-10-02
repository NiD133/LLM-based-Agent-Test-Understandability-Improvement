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
public class EnumUtils_ESTest_test11 extends EnumUtils_ESTest_scaffolding {

    private static final Class<Locale.FilteringMode> FILTERING_MODE_CLASS = Locale.FilteringMode.class;
    private static final long BIT_VECTOR_WITH_ONE_FILTERING_MODE = 2800L;
    private static final int EXPECTED_FILTERING_MODE_COUNT = 1;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        EnumSet<Locale.FilteringMode> filteringModes =
                EnumUtils.processBitVector(FILTERING_MODE_CLASS, BIT_VECTOR_WITH_ONE_FILTERING_MODE);

        assertEquals(EXPECTED_FILTERING_MODE_COUNT, filteringModes.size());
    }
}
