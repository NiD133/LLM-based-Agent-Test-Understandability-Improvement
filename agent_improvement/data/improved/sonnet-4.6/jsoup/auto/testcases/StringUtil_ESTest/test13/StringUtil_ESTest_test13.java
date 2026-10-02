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
public class StringUtil_ESTest_test13 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_resolveEmptyRelativeUrl_returnsBaseUrl() throws Throwable {
        URL baseUrl = MockURL.getFileExample();
        URL resolvedUrl = StringUtil.resolve(baseUrl, "");
        assertTrue(resolvedUrl.equals((Object) baseUrl));
    }
}
