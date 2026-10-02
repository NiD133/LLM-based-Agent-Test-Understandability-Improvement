package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collector;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test25 extends StringUtil_ESTest_scaffolding {

    // Unicode 160 is the non-breaking space character (&nbsp;), which should be
    // treated as whitespace for element text extraction purposes even though it
    // is not whitespace per the HTML output spec.
    private static final int NON_BREAKING_SPACE = 160;

    @Test(timeout = 4000)
    public void test_nonBreakingSpaceCodePoint_isRecognizedAsActualWhitespace() throws Throwable {
        boolean isWhitespace = StringUtil.isActuallyWhitespace(NON_BREAKING_SPACE);
        assertTrue(isWhitespace);
    }
}
