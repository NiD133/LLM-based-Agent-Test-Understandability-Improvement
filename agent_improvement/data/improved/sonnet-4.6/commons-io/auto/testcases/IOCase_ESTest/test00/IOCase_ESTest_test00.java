package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test00 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_checkStartsWith_returnsFalse_whenPrefixIsLongerThanStr() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        String str = "zmIl1;deJ|AOW";
        String longerPrefix = " without breaking the first codepoint oV grapheme;c!8-T\r";

        boolean result = systemCase.checkStartsWith(str, longerPrefix);

        assertFalse(result);
    }
}
