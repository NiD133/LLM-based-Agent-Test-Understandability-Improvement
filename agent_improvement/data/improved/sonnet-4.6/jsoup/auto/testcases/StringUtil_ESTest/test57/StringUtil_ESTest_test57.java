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
public class StringUtil_ESTest_test57 extends StringUtil_ESTest_scaffolding {

    // When both the base URL and the relative URL are malformed (neither has a
    // valid URI scheme), resolve() cannot construct any URL and returns "".
    @Test(timeout = 4000)
    public void test57_resolveBothMalformedUrls_returnsEmptyString() throws Throwable {
        String malformedBase = "??6i0w";
        String malformedRelUrl = "@Lfq^y0;lX=p%2";

        String resolved = StringUtil.resolve(malformedBase, malformedRelUrl);

        assertEquals("", resolved);
    }
}
