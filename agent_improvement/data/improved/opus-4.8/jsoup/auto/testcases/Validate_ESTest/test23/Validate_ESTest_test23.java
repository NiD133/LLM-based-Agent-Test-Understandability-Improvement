package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test23 extends Validate_ESTest_scaffolding {

    /**
     * When the object passed to ensureNotNull is not null, the method should
     * return that same object unchanged (the message and format arguments are
     * only used to build the exception thrown for a null object).
     */
    @Test(timeout = 4000)
    public void ensureNotNullReturnsSameObjectWhenNotNull() throws Throwable {
        String nonNullObject = ".;(s<;hD";
        String formatMessage = ".;(s<;hD";
        Object[] noFormatArguments = null;

        Object returned = Validate.ensureNotNull(nonNullObject, formatMessage, noFormatArguments);

        assertEquals(nonNullObject, returned);
    }
}
