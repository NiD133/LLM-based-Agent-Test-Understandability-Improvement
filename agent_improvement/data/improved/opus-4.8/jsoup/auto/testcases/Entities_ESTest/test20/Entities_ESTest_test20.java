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
     * Verifies that {@link Entities#isNamedEntity(String)} returns false for an
     * arbitrary string that is not a recognised HTML named entity.
     */
    @Test(timeout = 4000)
    public void isNamedEntityReturnsFalseForUnknownName() throws Throwable {
        String unknownEntityName = "|TM1yKJ";

        boolean isKnownEntity = Entities.isNamedEntity(unknownEntityName);

        assertFalse("Arbitrary string should not be a known named entity", isKnownEntity);
    }
}
