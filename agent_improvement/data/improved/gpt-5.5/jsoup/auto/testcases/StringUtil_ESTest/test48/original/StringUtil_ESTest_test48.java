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
public class StringUtil_ESTest_test48 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test48() throws Throwable {
        String string0 = StringUtil.padding(5182, 5182);
        StringBuilder stringBuilder0 = new StringBuilder(string0);
        StringBuilder stringBuilder1 = stringBuilder0.insert(5182, (CharSequence) stringBuilder0);
        StringUtil.releaseBuilderVoid(stringBuilder0);
        assertSame(stringBuilder0, stringBuilder1);
    }
}
