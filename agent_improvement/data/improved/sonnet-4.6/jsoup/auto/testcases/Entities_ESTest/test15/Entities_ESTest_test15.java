package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test15 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_findPrefix_returnsEmpty_whenInputHasNoKnownEntityPrefix() throws Throwable {
        // "a%VaysnL|7L=rC" starts with 'a' but the '%' prevents matching any HTML base entity name
        String matchedPrefix = Entities.findPrefix("a%VaysnL|7L=rC");
        assertEquals("", matchedPrefix);
    }
}
