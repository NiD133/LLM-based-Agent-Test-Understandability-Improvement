package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.format.TextStyle;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test25 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that the short-style display name of the fourth quarter is "Q4",
     * regardless of locale (here, Japanese), since no localized text mapping
     * exists and the formatter falls back to the standard "Q" + value form.
     */
    @Test(timeout = 4000)
    public void displayNameOfQ4InShortStyleIsQ4() throws Throwable {
        Quarter fourthQuarter = Quarter.of(4);

        String displayName = fourthQuarter.getDisplayName(TextStyle.SHORT, Locale.JAPAN);

        assertEquals("Q4", displayName);
    }
}
