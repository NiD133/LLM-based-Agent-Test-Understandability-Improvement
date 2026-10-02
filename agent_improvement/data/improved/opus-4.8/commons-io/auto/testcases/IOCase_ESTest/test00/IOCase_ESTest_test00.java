package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test00 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkStartsWith(String, String)} returns
     * false when the given string does not start with the supplied prefix.
     */
    @Test(timeout = 4000)
    public void checkStartsWith_returnsFalse_whenStringDoesNotStartWithPrefix() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        String text = "zmIl1;deJ|AOW";
        String prefix = " without breaking the first codepoint oV grapheme;c!8-T\r";

        boolean startsWithPrefix = systemCase.checkStartsWith(text, prefix);

        assertFalse(startsWithPrefix);
    }
}
