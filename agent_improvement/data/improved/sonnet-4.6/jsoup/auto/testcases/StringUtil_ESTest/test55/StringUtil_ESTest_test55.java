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
public class StringUtil_ESTest_test55 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that appendNormalisedWhitespace with stripLeading=true preserves
     * trailing whitespace when the string starts with a non-whitespace character.
     * "? " begins with '?', so the trailing space is kept (stripping only applies
     * to leading whitespace before any non-whitespace character is encountered).
     */
    @Test(timeout = 4000)
    public void test_appendNormalisedWhitespace_stripLeading_trailingSpacePreserved() throws Throwable {
        StringBuilder accum = StringUtil.borrowBuilder();
        StringUtil.appendNormalisedWhitespace(accum, "? ", true);
        assertEquals("? ", accum.toString());
    }
}
