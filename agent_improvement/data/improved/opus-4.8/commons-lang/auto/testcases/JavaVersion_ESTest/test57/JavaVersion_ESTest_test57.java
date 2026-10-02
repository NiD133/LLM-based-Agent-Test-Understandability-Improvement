package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test57 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string above the highest explicitly enumerated Java release
     * (any value greater than 10 that is not a known constant) is mapped to
     * {@link JavaVersion#JAVA_RECENT}.
     */
    @Test(timeout = 4000)
    public void getUnknownHighVersionReturnsJavaRecent() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("99.0");

        assertEquals(JavaVersion.JAVA_RECENT, resolvedVersion);
    }
}
