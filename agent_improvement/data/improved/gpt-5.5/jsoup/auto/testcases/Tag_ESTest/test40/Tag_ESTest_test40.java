package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test40 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test40() throws Throwable {
        Tag uppercaseAnchorTag = new Tag("A");
        int voidTagOption = 2;

        Tag configuredTag = uppercaseAnchorTag.set(voidTagOption);
        boolean isSelfClosingAfterSettingVoidOption = configuredTag.isSelfClosing();

        assertTrue(uppercaseAnchorTag.isEmpty());
        assertTrue(isSelfClosingAfterSettingVoidOption);
    }
}
