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
public class StringUtil_ESTest_test21 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtil#in(String, String...)} returns {@code false}
     * when the needle does not equal any element of the haystack.
     */
    @Test(timeout = 4000)
    public void in_returnsFalse_whenNeedleNotPresentInHaystack() throws Throwable {
        String needle = "-l:l9,u'ew>";
        String[] haystack = {
            "",
            "^[a-zA-Z][a-zA-Z0-9+-.]*:",
            "^[a-zA-Z][a-zA-Z0-9+-.]*:",
            "d+"
        };

        boolean found = StringUtil.in(needle, haystack);

        assertFalse(found);
    }
}
