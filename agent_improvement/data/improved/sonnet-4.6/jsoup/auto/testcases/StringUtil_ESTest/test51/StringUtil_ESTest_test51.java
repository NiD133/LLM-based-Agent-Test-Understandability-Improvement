package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test51 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_joinWithEmptyIterator_returnsEmptyString() throws Throwable {
        // An empty list produces an empty iterator — joining it should yield ""
        LinkedList<StringBuilder> emptyList = new LinkedList<StringBuilder>();
        ListIterator<StringBuilder> emptyIterator = emptyList.listIterator();

        String result = StringUtil.join((Iterator<?>) emptyIterator, "");

        assertEquals("", result);
    }
}
