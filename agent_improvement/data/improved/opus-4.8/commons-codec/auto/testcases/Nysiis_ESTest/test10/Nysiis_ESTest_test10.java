package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test10 extends Nysiis_ESTest_scaffolding {

    /**
     * A default {@link Nysiis} encoder runs in strict mode and encodes "kSCH" to "C":
     * after cleaning to upper case "KSCH", the leading "K" maps to "C" and the
     * trailing "SCH" collapses away, leaving the single-character key "C".
     */
    @Test(timeout = 4000)
    public void testEncodeKschInStrictModeYieldsC() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        String encoded = defaultEncoder.encode("kSCH");

        assertTrue("default encoder should use strict mode", defaultEncoder.isStrict());
        assertNotNull("encoding should never return null for non-null input", encoded);
        assertEquals("C", encoded);
    }
}
