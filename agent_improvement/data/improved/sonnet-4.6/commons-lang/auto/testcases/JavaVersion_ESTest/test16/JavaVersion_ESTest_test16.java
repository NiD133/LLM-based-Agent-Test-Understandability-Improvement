package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test16 extends JavaVersion_ESTest_scaffolding {

    /**
     * "0X" is not a recognised Java version string. It falls through to the default
     * branch of {@code JavaVersion.get()}, where the code tries to parse the substring
     * after a missing decimal separator via {@code Float.parseFloat}, which throws
     * {@code NumberFormatException} because "0X" is not a valid floating-point literal.
     */
    @Test(timeout = 4000)
    public void testGetWithHexPrefixStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("0X");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
