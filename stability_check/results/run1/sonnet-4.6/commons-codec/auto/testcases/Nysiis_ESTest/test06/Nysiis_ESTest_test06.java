package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test06 extends Nysiis_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Nysiis nysiis = new Nysiis();

        // A dotted class path string exercises multi-segment encoding;
        // strict mode caps the result at 6 characters.
        Object encoded = nysiis.encode((Object) "org.apache.commons.codec.EncodeyException");

        assertNotNull(encoded);
        assertEquals("ORGAPA", encoded);
    }
}
