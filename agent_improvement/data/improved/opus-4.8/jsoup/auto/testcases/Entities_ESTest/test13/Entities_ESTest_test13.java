package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test13 extends Entities_ESTest_scaffolding {

    /**
     * Escaping a null input should yield an empty string rather than throwing,
     * because {@link Entities#escape(String)} treats null as having nothing to escape.
     */
    @Test(timeout = 4000)
    public void escapeNullInputReturnsEmptyString() throws Throwable {
        String escaped = Entities.escape((String) null);

        assertEquals("", escaped);
    }
}
