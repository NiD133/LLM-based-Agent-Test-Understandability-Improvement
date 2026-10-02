package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test06 extends Validate_ESTest_scaffolding {

    // Verifies that notEmpty does not throw when the string is non-null and non-empty.
    @Test(timeout = 4000)
    public void notEmpty_doesNotThrow_whenStringIsNonEmpty() throws Throwable {
        Validate.notEmpty("yr`o{,Pr'v!D5M");
    }
}
