package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test03 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that encoding an empty string does not affect the strict mode flag.
     * The default Nysiis constructor enables strict mode (max 6-character output),
     * and that setting should remain unchanged after encoding an empty input.
     */
    @Test(timeout = 4000)
    public void test_defaultConstructorEnablesStrictMode_afterEncodingEmptyString() throws Throwable {
        Nysiis nysiis = new Nysiis();

        // Encoding an empty string is a no-op (returns empty string per the algorithm)
        nysiis.nysiis("");

        // The default constructor sets strict = true; encoding should not alter it
        assertTrue(nysiis.isStrict());
    }
}
