package org.jsoup.nodes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test27 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that {@link Entities#unescape(String)} converts the HTML entity
     * "&lt;" back into the literal "less-than" character.
     */
    @Test(timeout = 4000)
    public void unescapeLtEntityReturnsLessThanCharacter() throws Throwable {
        String unescaped = Entities.unescape("&lt;");

        assertEquals("<", unescaped);
    }
}
