package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test31 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notNull should accept a non-null object and return normally,
     * without throwing a ValidationException.
     */
    @Test(timeout = 4000)
    public void notNull_withNonNullObject_doesNotThrow() throws Throwable {
        Object nonNullObject = "j*C";

        Validate.notNull(nonNullObject);
    }
}
