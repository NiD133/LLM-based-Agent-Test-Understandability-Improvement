package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.Map;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test13 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#getEnumMap(Class)} builds a non-empty map
     * when given an enum type that actually declares constants. Here we use the
     * JDK's {@link Locale.FilteringMode} enum, which has several constants, so
     * the resulting name-to-enum map must contain entries.
     */
    @Test(timeout = 4000)
    public void getEnumMap_forEnumWithConstants_returnsNonEmptyMap() throws Throwable {
        Map<String, Locale.FilteringMode> enumsByName =
                EnumUtils.getEnumMap(Locale.FilteringMode.class);

        assertFalse("Map should contain an entry for each enum constant", enumsByName.isEmpty());
    }
}
