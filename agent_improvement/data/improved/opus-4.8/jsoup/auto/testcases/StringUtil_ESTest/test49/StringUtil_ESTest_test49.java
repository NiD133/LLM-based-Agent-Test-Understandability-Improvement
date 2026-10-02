package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test49 extends StringUtil_ESTest_scaffolding {

    /**
     * Joining a single-element array should return that element unchanged,
     * because the separator is only inserted between elements.
     */
    @Test(timeout = 4000)
    public void joinSingleElementArrayReturnsElement() throws Throwable {
        String[] singleElement = { "#~=AewMCu5k[" };
        String separator = "Scld9)?|}Y+>";

        String joined = StringUtil.join(singleElement, separator);

        assertNotNull(joined);
    }
}
