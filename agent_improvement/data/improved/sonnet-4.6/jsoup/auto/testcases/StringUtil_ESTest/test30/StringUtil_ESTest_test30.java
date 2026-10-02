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
public class StringUtil_ESTest_test30 extends StringUtil_ESTest_scaffolding {

    // ASCII 13 is carriage return '\r', which the HTML spec classifies as whitespace
    private static final int CARRIAGE_RETURN = '\r';

    @Test(timeout = 4000)
    public void test_isWhitespace_carriageReturn_returnsTrue() throws Throwable {
        boolean result = StringUtil.isWhitespace(CARRIAGE_RETURN);
        assertTrue(result);
    }
}
