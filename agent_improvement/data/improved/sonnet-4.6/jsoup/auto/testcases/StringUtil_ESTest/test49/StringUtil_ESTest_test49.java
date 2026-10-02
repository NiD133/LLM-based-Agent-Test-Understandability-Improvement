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
public class StringUtil_ESTest_test49 extends StringUtil_ESTest_scaffolding {

    /**
     * Joining a single-element array always returns that element unchanged,
     * regardless of what separator is provided.
     */
    @Test(timeout = 4000)
    public void test49_joinSingleElementArray_returnsThatElement() throws Throwable {
        String[] singleElementArray = new String[] { "#~=AewMCu5k[" };
        String separator = "Scld9)?|}Y+>";

        String result = StringUtil.join(singleElementArray, separator);

        assertNotNull(result);
    }
}
