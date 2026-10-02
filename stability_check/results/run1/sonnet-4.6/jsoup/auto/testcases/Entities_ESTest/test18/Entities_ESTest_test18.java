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
    public void test_isBaseNamedEntity_returnsFalse_forUnrecognizedEntityName() throws Throwable {
        // "LQ2" is not a valid HTML base named entity (base set contains entries like "lt", "gt", "amp", etc.)
        String unrecognizedEntityName = "LQ2";
        boolean isKnownBaseEntity = Entities.isBaseNamedEntity(unrecognizedEntityName);
        assertFalse(isKnownBaseEntity);
    }
}
