package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test22 extends Entities_ESTest_scaffolding {

    /**
     * Looking up the codepoint for '>' (62) in the xhtml escape mode
     * should return the entity name "gt" (as in {@code &gt;}).
     */
    @Test(timeout = 4000)
    public void nameForCodepoint_greaterThanInXhtmlMode_returnsGt() throws Throwable {
        Entities.EscapeMode xhtmlMode = Entities.EscapeMode.xhtml;
        int greaterThanCodepoint = 62; // '>'

        String entityName = xhtmlMode.nameForCodepoint(greaterThanCodepoint);

        assertEquals("gt", entityName);
    }
}
