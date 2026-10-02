package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test22 extends Entities_ESTest_scaffolding {

    // Unicode codepoint 62 is the '>' (greater-than) character
    private static final int GREATER_THAN_CODEPOINT = 62;

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Entities.EscapeMode xhtmlMode = Entities.EscapeMode.xhtml;
        String entityName = xhtmlMode.nameForCodepoint(GREATER_THAN_CODEPOINT);
        assertEquals("gt", entityName);
    }
}
