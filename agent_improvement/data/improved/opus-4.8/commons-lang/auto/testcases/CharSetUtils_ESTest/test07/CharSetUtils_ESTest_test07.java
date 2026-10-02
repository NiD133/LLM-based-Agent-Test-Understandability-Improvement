package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test07 extends CharSetUtils_ESTest_scaffolding {

    /**
     * When the source string is empty, {@link CharSetUtils#keep(String, String...)}
     * returns an empty string regardless of the character set provided.
     */
    @Test(timeout = 4000)
    public void keepWithEmptyStringReturnsEmptyString() throws Throwable {
        String emptySource = "";
        String[] characterSet = new String[9];

        String result = CharSetUtils.keep(emptySource, characterSet);

        assertEquals("", result);
    }
}
