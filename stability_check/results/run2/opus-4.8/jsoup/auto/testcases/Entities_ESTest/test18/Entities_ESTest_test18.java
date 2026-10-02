package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test18 extends Entities_ESTest_scaffolding {

    /**
     * "LQ2" is not a member of the base HTML entity set, so
     * {@link Entities#isBaseNamedEntity(String)} should report it as unknown.
     */
    @Test(timeout = 4000)
    public void isBaseNamedEntity_returnsFalse_forUnknownName() throws Throwable {
        boolean isKnownBaseEntity = Entities.isBaseNamedEntity("LQ2");

        assertFalse(isKnownBaseEntity);
    }
}
