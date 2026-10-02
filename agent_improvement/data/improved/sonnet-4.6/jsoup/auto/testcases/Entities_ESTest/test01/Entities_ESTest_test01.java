package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test01 extends Entities_ESTest_scaffolding {

    /**
     * An empty string does not match "US-ASCII" and does not start with "UTF-",
     * so CoreCharset.byName should return the fallback charset.
     */
    @Test(timeout = 4000)
    public void test_byName_withUnrecognisedName_returnsFallback() throws Throwable {
        Entities.CoreCharset result = Entities.CoreCharset.byName("");
        assertEquals(Entities.CoreCharset.fallback, result);
    }
}
