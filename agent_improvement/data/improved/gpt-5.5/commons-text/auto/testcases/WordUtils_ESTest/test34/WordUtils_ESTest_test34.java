package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test34 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void abbreviateReturnsEmptyStringWhenInputIsEmpty() throws Throwable {
        String abbreviatedText = WordUtils.abbreviate("", 0, 1308, "");

        assertEquals("", abbreviatedText);
    }
}
