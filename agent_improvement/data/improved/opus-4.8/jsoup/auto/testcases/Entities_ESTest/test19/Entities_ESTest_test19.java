package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test19 extends Entities_ESTest_scaffolding {

    /**
     * "sup2" is a known HTML named entity (the superscript two character),
     * so {@link Entities#isNamedEntity(String)} should report it as recognized.
     */
    @Test(timeout = 4000)
    public void isNamedEntity_returnsTrue_forKnownEntityName() throws Throwable {
        boolean recognized = Entities.isNamedEntity("sup2");

        assertTrue("'sup2' should be recognized as a known named entity", recognized);
    }
}
