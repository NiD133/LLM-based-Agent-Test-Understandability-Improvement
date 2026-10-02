package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test03 extends Validate_ESTest_scaffolding {

    private static final String NON_EMPTY_PARAMETER_VALUE = "Array must not contain any null objects";
    private static final String PARAMETER_NAME = "Array must not contain any null objects";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Validate.notEmptyParam(NON_EMPTY_PARAMETER_VALUE, PARAMETER_NAME);
    }
}
