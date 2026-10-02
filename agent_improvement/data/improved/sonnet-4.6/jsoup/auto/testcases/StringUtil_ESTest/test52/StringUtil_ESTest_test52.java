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
public class StringUtil_ESTest_test52 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that StringJoiner.append() supports method chaining by returning
     * the same joiner instance, even when both the separator and the appended
     * object are null.
     */
    @Test(timeout = 4000)
    public void test52() throws Throwable {
        StringUtil.StringJoiner joiner = new StringUtil.StringJoiner((String) null);
        StringUtil.StringJoiner returnedJoiner = joiner.append((Object) null);

        assertSame("append() must return the same StringJoiner instance to support method chaining",
                joiner, returnedJoiner);
    }
}
