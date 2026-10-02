package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test18 extends Validate_ESTest_scaffolding {

    /**
     * When {@code expectNotNull} is called with a null object and a null format message,
     * it tries to build the validation message via {@code String.format(null, args)},
     * which throws a raw {@link NullPointerException} (with no message).
     */
    @Test(timeout = 4000)
    public void expectNotNullWithNullMessageThrowsNullPointerException() throws Throwable {
        Object nullObject = null;
        String nullMessage = null;
        Object[] formatArgs = new Object[5];

        try {
            Validate.expectNotNull(nullObject, nullMessage, formatArgs);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // String.format(null, ...) throws an NPE with no message
        }
    }
}
