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

    @Test(timeout = 4000)
    public void test49() throws Throwable {
        String[] stringArray0 = new String[1];
        stringArray0[0] = "#~=AewMCu5k[";
        String string0 = StringUtil.join(stringArray0, "Scld9)?|}Y+>");
        assertNotNull(string0);
    }
}
