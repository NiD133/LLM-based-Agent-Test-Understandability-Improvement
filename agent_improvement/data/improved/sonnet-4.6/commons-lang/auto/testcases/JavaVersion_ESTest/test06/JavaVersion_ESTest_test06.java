package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test06 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that JavaVersion.get() returns JAVA_RECENT for an unrecognized version string
     * whose decimal part (the character after '.') is '9', which exceeds the 0.9f threshold
     * in the default case of the get() method's version-parsing logic.
     *
     * The input "'}wb,.9" does not match any known version; its float parse yields -1 (< 1),
     * so the method inspects the substring after the last '.' separator and finds "9" > 0.9f,
     * triggering the JAVA_RECENT return path.
     */
    @Test(timeout = 4000)
    public void test_getWithUnrecognizedVersionStringHavingDecimalAboveThreshold_returnsJavaRecent() throws Throwable {
        // A garbled string that does not match any known Java version but whose
        // post-dot fragment ("9") passes the >0.9f check, so JAVA_RECENT is returned.
        String unrecognizedVersionWithHighDecimal = "'}wb,.9";

        JavaVersion result = JavaVersion.get(unrecognizedVersionWithHighDecimal);

        assertEquals(JavaVersion.JAVA_RECENT, result);
    }
}
