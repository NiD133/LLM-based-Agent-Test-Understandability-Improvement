package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test19 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that "sup2" (superscript two, ²) is recognized as a valid named HTML entity
     * in the extended entity set.
     */
    @Test(timeout = 4000)
    public void test_sup2_isKnownNamedEntity() throws Throwable {
        boolean isSup2ANamedEntity = Entities.isNamedEntity("sup2");
        assertTrue("'sup2' should be a recognized HTML named entity", isSup2ANamedEntity);
    }
}
