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
public class StringUtil_ESTest_test14 extends StringUtil_ESTest_scaffolding {

    private static final int ASCII_NUL_PREFIX_LENGTH = 448;
    private static final char NON_ASCII_CHARACTER = '\u01C0';

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        String asciiPrefixFollowedByNonAscii = repeatedNulsFollowedByNonAscii();

        boolean isAscii = StringUtil.isAscii(asciiPrefixFollowedByNonAscii);

        assertFalse(isAscii);
    }

    private static String repeatedNulsFollowedByNonAscii() {
        StringBuilder input = new StringBuilder(ASCII_NUL_PREFIX_LENGTH + 1);
        for (int i = 0; i < ASCII_NUL_PREFIX_LENGTH; i++) {
            input.append('\u0000');
        }
        input.append(NON_ASCII_CHARACTER);
        return input.toString();
    }
}
