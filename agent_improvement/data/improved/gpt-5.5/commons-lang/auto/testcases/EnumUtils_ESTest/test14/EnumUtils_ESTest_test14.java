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
public class EnumUtils_ESTest_test14 extends EnumUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        final Class<Locale.FilteringMode> nullEnumClass = null;
        final String unknownEnumName = "HaEk[w(YT.?P#4B";

        final boolean isValidEnum = EnumUtils.isValidEnum(nullEnumClass, unknownEnumName);

        assertFalse(isValidEnum);
    }
}
