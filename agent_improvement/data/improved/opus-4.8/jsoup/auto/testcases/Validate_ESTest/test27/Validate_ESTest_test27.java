package org.jsoup.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test27 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notNull(obj, msg) should accept a non-null object without
     * throwing a ValidationException. The test passes simply by completing
     * the call normally.
     */
    @Test(timeout = 4000)
    public void notNull_withNonNullObject_doesNotThrow() throws Throwable {
        Integer nonNullObject = Integer.valueOf(1004);

        Validate.notNull((Object) nonNullObject, "");
    }
}
