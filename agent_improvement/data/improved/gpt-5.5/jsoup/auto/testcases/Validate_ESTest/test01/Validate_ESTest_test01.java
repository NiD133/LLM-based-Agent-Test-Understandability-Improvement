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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        String nonEmptyInput = "BQ#hSC'iWZHd+H4x";
        String validationFailureMessage = "Array must not contain any null objects";

        Validate.notEmpty(nonEmptyInput, validationFailureMessage);
    }
}
