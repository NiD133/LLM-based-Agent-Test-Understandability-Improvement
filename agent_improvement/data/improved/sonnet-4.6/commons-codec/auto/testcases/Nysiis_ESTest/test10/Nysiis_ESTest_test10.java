package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test10 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that "kSCH" encodes to "C" under the default strict-mode Nysiis encoder.
     *
     * The encoding proceeds as follows:
     *   1. Clean input to uppercase: "KSCH"
     *   2. Leading K -> C (rule 1c):  "CSCH"
     *   3. Remaining SCH -> SSS (rule 4f), duplicate S's collapsed, trailing S removed (rule 5)
     *   4. Final result: "C"
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Nysiis encoder = new Nysiis();

        String encoded = encoder.encode("kSCH");

        assertNotNull(encoded);
        assertTrue(encoder.isStrict());
        assertEquals("C", encoded);
    }
}
