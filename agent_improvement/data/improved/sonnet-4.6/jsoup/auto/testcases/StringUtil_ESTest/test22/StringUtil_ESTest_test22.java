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
public class StringUtil_ESTest_test22 extends StringUtil_ESTest_scaffolding {

    /**
     * When stripLeading=true and the input contains only whitespace, every character is
     * classified as "leading whitespace" and skipped, so the accumulator (accum) is never
     * written to. Passing null for accum is therefore safe: the null reference is never
     * dereferenced and no NullPointerException is thrown.
     */
    @Test(timeout = 4000)
    public void appendNormalisedWhitespace_nullAccumWithOnlyLeadingWhitespace_doesNotThrow() throws Throwable {
        String allWhitespace = "          "; // 10 spaces — every character will be stripped as leading whitespace
        boolean stripLeading = true;

        // Should complete without throwing, because accum is never accessed when all content is stripped
        StringUtil.appendNormalisedWhitespace((StringBuilder) null, allWhitespace, stripLeading);
    }
}
