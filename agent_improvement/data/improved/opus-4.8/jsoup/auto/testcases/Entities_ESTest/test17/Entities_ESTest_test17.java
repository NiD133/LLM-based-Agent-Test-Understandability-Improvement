package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test17 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that looking up the named HTML entity "QUOT" returns the
     * double-quote character it represents.
     */
    @Test(timeout = 4000)
    public void getByName_returnsDoubleQuoteForQuotEntity() throws Throwable {
        String resolvedCharacter = Entities.getByName("QUOT");

        assertNotNull(resolvedCharacter);
        assertEquals("\"", resolvedCharacter);
    }
}
