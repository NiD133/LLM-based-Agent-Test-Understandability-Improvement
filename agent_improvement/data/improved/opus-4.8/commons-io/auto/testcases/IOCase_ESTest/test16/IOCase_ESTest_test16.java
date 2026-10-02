package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test16 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkEndsWith(String, String)} returns false
     * when the candidate suffix is not actually the ending of the string,
     * even under case-insensitive matching.
     */
    @Test(timeout = 4000)
    public void checkEndsWith_whenSuffixDoesNotMatch_returnsFalse() throws Throwable {
        IOCase insensitive = IOCase.INSENSITIVE;
        String text = "System";
        String suffix = "z:=F{9w=V$70~Oy";

        boolean endsWith = insensitive.checkEndsWith(text, suffix);

        assertFalse(endsWith);
    }
}
