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
        final int paddingWidth = 5182;
        final int maxPaddingWidth = 5182;

        String padding = StringUtil.padding(paddingWidth, maxPaddingWidth);
        StringBuilder builder = new StringBuilder(padding);
        StringBuilder returnedBuilder = builder.insert(paddingWidth, (CharSequence) builder);

        StringUtil.releaseBuilderVoid(builder);

        assertSame(builder, returnedBuilder);
    }
}
