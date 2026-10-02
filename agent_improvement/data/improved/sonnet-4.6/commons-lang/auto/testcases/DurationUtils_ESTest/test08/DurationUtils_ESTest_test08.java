package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test08 extends DurationUtils_ESTest_scaffolding {

    /**
     * When no system property key is provided (null), getMillis should fall back
     * to the supplied default value and return a non-null Duration.
     */
    @Test(timeout = 4000)
    public void test_getMillis_nullKey_returnsNonNullDurationFromDefault() throws Throwable {
        long defaultMillis = -1048L;
        Duration result = DurationUtils.getMillis((String) null, defaultMillis);
        assertNotNull(result);
    }
}
