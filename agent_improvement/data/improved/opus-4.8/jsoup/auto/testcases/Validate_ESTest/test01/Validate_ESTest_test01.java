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

    /**
     * Validate.notEmpty(String, msg) should accept a non-empty string and
     * return normally without throwing a ValidationException.
     */
    @Test(timeout = 4000)
    public void notEmpty_withNonEmptyString_doesNotThrow() throws Throwable {
        String nonEmptyString = "BQ#hSC'iWZHd+H4x";
        String validationMessage = "Array must not contain any null objects";

        // No exception expected: the string is non-empty, so validation passes.
        Validate.notEmpty(nonEmptyString, validationMessage);
    }
}
