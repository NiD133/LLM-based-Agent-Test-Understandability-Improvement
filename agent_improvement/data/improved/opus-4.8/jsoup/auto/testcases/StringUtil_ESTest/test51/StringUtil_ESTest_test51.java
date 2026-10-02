package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test51 extends StringUtil_ESTest_scaffolding {

    /**
     * Joining an empty iterator should yield an empty string, regardless of the separator.
     */
    @Test(timeout = 4000)
    public void joinEmptyIteratorReturnsEmptyString() throws Throwable {
        Iterator<StringBuilder> emptyIterator = new LinkedList<StringBuilder>().listIterator();

        String joined = StringUtil.join(emptyIterator, "");

        assertEquals("", joined);
    }
}
