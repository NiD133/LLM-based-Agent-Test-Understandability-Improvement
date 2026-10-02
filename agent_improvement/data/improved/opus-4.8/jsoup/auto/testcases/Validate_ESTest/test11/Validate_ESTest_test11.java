package org.jsoup.helper;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test11 extends Validate_ESTest_scaffolding {

    /**
     * When the value passed to {@link Validate#isFalse(boolean, String)} is already false,
     * the validation passes and no exception is thrown (the message is ignored).
     */
    @Test(timeout = 4000)
    public void isFalse_withFalseValue_doesNotThrow() throws Throwable {
        Validate.isFalse(false, "(ir;>>Z<W");
    }
}
