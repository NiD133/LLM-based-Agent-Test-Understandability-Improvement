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
public class StringUtil_ESTest_test50 extends StringUtil_ESTest_scaffolding {

    // Joining an array that contains an empty string followed by null elements
    // should still produce a non-null result.
    @Test(timeout = 4000)
    public void test_joinArrayWithEmptyFirstAndNullRemainingElementsReturnsNonNull() throws Throwable {
        String[] stringsWithNulls = new String[4];
        stringsWithNulls[0] = "";
        String result = StringUtil.join(stringsWithNulls, "?Must be true");
        assertNotNull(result);
    }
}
