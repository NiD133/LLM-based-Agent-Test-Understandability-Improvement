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
public class StringUtil_ESTest_test39 extends StringUtil_ESTest_scaffolding {

    // U+200B is the zero-width space character, which is not a newline
    private static final String ZERO_WIDTH_SPACE = "​";

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        boolean startsWithNewline = StringUtil.startsWithNewline(ZERO_WIDTH_SPACE);
        assertFalse(startsWithNewline);
    }
}
