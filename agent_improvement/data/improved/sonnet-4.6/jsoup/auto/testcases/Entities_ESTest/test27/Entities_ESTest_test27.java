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
    public void test27() throws Throwable {
        // &lt; is the HTML entity for the less-than sign '<'
        String unescapedLessThan = Entities.unescape("&lt;");
        assertEquals("<", unescapedLessThan);
    }
}
