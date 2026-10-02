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
public class StringUtil_ESTest_test23 extends StringUtil_ESTest_scaffolding {

    // Unicode U+00AD: soft hyphen — invisible formatting character
    private static final int SOFT_HYPHEN = 173;

    @Test(timeout = 4000)
    public void test23_softHyphenIsRecognisedAsInvisibleChar() throws Throwable {
        boolean isSoftHyphenInvisible = StringUtil.isInvisibleChar(SOFT_HYPHEN);
        assertTrue("Soft hyphen (U+00AD, code point 173) should be classified as an invisible character",
            isSoftHyphenInvisible);
    }
}
