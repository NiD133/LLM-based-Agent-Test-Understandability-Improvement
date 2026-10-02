package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test20 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that a string containing characters invalid in an HTML entity name
     * (e.g. '|') is not recognised as a named entity.
     */
    @Test(timeout = 4000)
    public void test_isNamedEntity_returnsFalse_forInvalidEntityName() throws Throwable {
        boolean isEntity = Entities.isNamedEntity("|TM1yKJ");
        assertFalse(isEntity);
    }
}
