package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test23 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        String nonNullValue = ".;(s<;hD";

        Object result = Validate.ensureNotNull((Object) nonNullValue, nonNullValue, (Object[]) null);

        assertEquals(nonNullValue, result);
    }
}
