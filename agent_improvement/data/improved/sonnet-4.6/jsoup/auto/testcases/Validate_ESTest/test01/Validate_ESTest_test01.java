package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test01 extends Validate_ESTest_scaffolding {

    // Verifies that notEmpty(string, msg) does not throw when the string is non-empty.
    @Test(timeout = 4000)
    public void test_notEmpty_nonEmptyString_doesNotThrow() throws Throwable {
        Validate.notEmpty("BQ#hSC'iWZHd+H4x", "Array must not contain any null objects");
    }
}
