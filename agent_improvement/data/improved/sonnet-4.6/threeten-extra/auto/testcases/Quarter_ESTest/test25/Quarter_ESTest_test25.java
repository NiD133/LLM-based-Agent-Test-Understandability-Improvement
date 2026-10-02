package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.TextStyle;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test25 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getDisplayName_shortStyle_japanLocale_returnsQ4() throws Throwable {
        Quarter q4 = Quarter.of(4);
        String displayName = q4.getDisplayName(TextStyle.SHORT, Locale.JAPAN);
        assertEquals("Q4", displayName);
    }
}
