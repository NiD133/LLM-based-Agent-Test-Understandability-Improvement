package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test18 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isBaseNamedEntity_returnsFalseForUnrecognizedName() throws Throwable {
        // "LQ2" is not a valid HTML base entity name, so it should not be recognized
        boolean isKnownBaseEntity = Entities.isBaseNamedEntity("LQ2");
        assertFalse(isKnownBaseEntity);
    }
}
