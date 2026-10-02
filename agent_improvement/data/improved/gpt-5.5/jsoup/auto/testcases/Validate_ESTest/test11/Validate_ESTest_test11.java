package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test11 extends Validate_ESTest_scaffolding {

    private static final boolean VALID_FALSE_VALUE = false;
    private static final String FAILURE_MESSAGE = "(ir;>>Z<W";

    @Test(timeout = 4000)
    public void isFalseAllowsFalseValue() throws Throwable {
        Validate.isFalse(VALID_FALSE_VALUE, FAILURE_MESSAGE);
    }
}
