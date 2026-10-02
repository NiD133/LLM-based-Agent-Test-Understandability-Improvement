package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test19 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Object nonNullValue = new Object();
        Object[] unusedFormatArguments = new Object[8];
        String nullFailureMessage = "Array must not contain any null objects";

        Object returnedValue = Validate.expectNotNull(nonNullValue, nullFailureMessage, unusedFormatArguments);

        assertSame(nonNullValue, returnedValue);
    }
}
