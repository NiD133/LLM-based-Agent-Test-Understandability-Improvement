package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test44 extends StringUtil_ESTest_scaffolding {

    /**
     * A null string is considered blank, so {@link StringUtil#isBlank(String)} returns {@code true}.
     */
    @Test(timeout = 4000)
    public void isBlankReturnsTrueForNullString() throws Throwable {
        boolean blank = StringUtil.isBlank((String) null);

        assertTrue("null should be treated as blank", blank);
    }
}
