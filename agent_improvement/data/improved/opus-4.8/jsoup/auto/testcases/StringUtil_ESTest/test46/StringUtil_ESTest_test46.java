package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test46 extends StringUtil_ESTest_scaffolding {

    /**
     * When maxPaddingWidth is -1 (meaning "unlimited"), padding() must honour the
     * requested width without capping it, and so always returns a non-null string.
     */
    @Test(timeout = 4000)
    public void paddingWithUnlimitedMaxReturnsNonNullString() throws Throwable {
        int requestedWidth = 2485;
        int unlimitedMaxWidth = -1;

        String result = StringUtil.padding(requestedWidth, unlimitedMaxWidth);

        assertNotNull(result);
    }
}
