package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test57 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that an unrecognized version string greater than 10 (e.g. "99.0")
     * is treated as a future/unknown release and mapped to JAVA_RECENT.
     */
    @Test(timeout = 4000)
    public void test_get_withUnrecognizedFutureVersion_returnsJavaRecent() throws Throwable {
        // "99.0" does not match any known Java version constant; the fallback
        // branch in JavaVersion.get() treats any float value > 10 as JAVA_RECENT.
        JavaVersion result = JavaVersion.get("99.0");

        assertEquals(JavaVersion.JAVA_RECENT, result);
    }
}
