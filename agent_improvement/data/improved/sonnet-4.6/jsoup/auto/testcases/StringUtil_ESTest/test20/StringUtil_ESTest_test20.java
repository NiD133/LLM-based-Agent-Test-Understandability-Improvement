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
public class StringUtil_ESTest_test20 extends StringUtil_ESTest_scaffolding {

    // Tests that StringUtil.in() returns true when the needle matches the first element
    // of the haystack, short-circuiting before reaching null elements in the array.
    @Test(timeout = 4000)
    public void test_inReturnsTrueWhenNeedleMatchesFirstElement() throws Throwable {
        String[] haystack = new String[5]; // remaining 4 elements are null
        haystack[0] = "";

        boolean found = StringUtil.in("", haystack);

        assertTrue(found);
    }
}
