package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test40 extends WordUtils_ESTest_scaffolding {

    private static final String MESSAGE_TO_WRAP = "upper value cannot be less than -1";
    private static final int WRAP_WIDTH = 2;
    private static final String EXPECTED_WRAPPED_MESSAGE = "upper\r\nvalue\r\ncannot\r\nbe\r\nless\r\nthan\r\n-1";

    @Test(timeout = 4000)
    public void wrapsMessageAtWidthTwo() throws Throwable {
        String wrappedMessage = WordUtils.wrap(MESSAGE_TO_WRAP, WRAP_WIDTH);

        assertNotNull(wrappedMessage);
        assertEquals(EXPECTED_WRAPPED_MESSAGE, wrappedMessage);
    }
}
