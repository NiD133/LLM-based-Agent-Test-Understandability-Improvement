package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test13 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that getEnumMap returns a non-empty map for an enum class that has constants.
     * Locale.FilteringMode is a standard JDK enum with multiple values, so the resulting
     * name-to-enum map must contain at least one entry.
     */
    @Test(timeout = 4000)
    public void test_getEnumMap_returnsNonEmptyMap_forEnumWithConstants() throws Throwable {
        Map<String, Locale.FilteringMode> filteringModeByName = EnumUtils.getEnumMap(Locale.FilteringMode.class);
        assertFalse(filteringModeByName.isEmpty());
    }
}
