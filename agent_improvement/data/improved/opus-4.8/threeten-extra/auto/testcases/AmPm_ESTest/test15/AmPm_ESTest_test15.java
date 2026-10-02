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
public class AmPm_ESTest_test15 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that the full-style display name of AM, localized for Traditional
     * Chinese, is the expected Chinese morning indicator "上午".
     */
    @Test(timeout = 4000)
    public void getDisplayName_fullStyleTraditionalChinese_returnsChineseMorningText() throws Throwable {
        String displayName = AmPm.AM.getDisplayName(TextStyle.FULL, Locale.TRADITIONAL_CHINESE);

        assertEquals("上午", displayName);
    }
}
