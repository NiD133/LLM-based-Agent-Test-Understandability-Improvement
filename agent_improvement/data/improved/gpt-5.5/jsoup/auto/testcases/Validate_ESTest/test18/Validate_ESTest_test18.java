package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test18 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Object[] formatArguments = new Object[5];
        Object nullValue = formatArguments[0];
        String nullFormatMessage = (String) null;

        try {
            Validate.expectNotNull(nullValue, nullFormatMessage, formatArguments);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expected) {
            assertNull(expected.getMessage());
        }
    }
}
