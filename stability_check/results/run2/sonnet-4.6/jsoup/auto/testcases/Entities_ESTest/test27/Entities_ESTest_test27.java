package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test27 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_unescape_htmlLessThanEntity_returnsLessThanCharacter() throws Throwable {
        String unescaped = Entities.unescape("&lt;");
        assertEquals("<", unescaped);
    }
}
