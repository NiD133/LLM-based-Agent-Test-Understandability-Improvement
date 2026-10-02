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

    private static final String INVALID_HEXADECIMAL_STYLE_VERSION = "0X";

    @Test(timeout = 4000)
    public void test16RejectsInvalidHexadecimalStyleVersion() throws Throwable {
        try {
            JavaVersion.get(INVALID_HEXADECIMAL_STYLE_VERSION);
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
