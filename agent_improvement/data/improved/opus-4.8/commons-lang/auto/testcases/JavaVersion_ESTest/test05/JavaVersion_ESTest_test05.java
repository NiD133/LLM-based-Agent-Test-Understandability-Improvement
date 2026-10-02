package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test05 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} returns {@code null} for an
     * unrecognized version string. The value "3" matches none of the known
     * version constants and is not large enough to map to JAVA_RECENT, so the
     * lookup yields {@code null}.
     */
    @Test(timeout = 4000)
    public void getReturnsNullForUnknownVersionString() throws Throwable {
        JavaVersion result = JavaVersion.get("3");

        assertNull("Version string \"3\" is not a known Java version", result);
    }
}
