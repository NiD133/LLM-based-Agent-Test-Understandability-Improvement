package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test25 extends Validate_ESTest_scaffolding {

    /**
     * ensureNotNull should return the very same non-null object it was given.
     */
    @Test(timeout = 4000)
    public void ensureNotNull_returnsSameObject_whenInputIsNotNull() throws Throwable {
        Integer input = Integer.valueOf(-1);

        Object result = Validate.ensureNotNull((Object) input);

        assertEquals(-1, result);
    }
}
