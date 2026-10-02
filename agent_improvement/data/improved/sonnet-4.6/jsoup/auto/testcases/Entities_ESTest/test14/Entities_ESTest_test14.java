package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test14 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_findPrefix_returnsEntityName_whenInputExactlyMatchesBaseEntity() throws Throwable {
        // "shy" is a known base HTML entity (soft hyphen); findPrefix should return it as its own prefix
        String prefix = Entities.findPrefix("shy");
        assertEquals("shy", prefix);
    }
}
