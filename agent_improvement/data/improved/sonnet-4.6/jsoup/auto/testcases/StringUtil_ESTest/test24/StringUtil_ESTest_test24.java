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
public class StringUtil_ESTest_test24 extends StringUtil_ESTest_scaffolding {

    // U+200B is the zero-width space, classified as an invisible character by StringUtil.isInvisibleChar()
    private static final String ZERO_WIDTH_SPACE = "​";

    @Test(timeout = 4000)
    public void test24_zeroWidthSpaceIsDiscardedDuringNormalization() throws Throwable {
        StringBuilder accum = new StringBuilder();

        StringUtil.appendNormalisedWhitespace(accum, ZERO_WIDTH_SPACE, true);

        assertEquals("Zero-width space should be silently dropped and produce no output",
                "", accum.toString());
    }
}
