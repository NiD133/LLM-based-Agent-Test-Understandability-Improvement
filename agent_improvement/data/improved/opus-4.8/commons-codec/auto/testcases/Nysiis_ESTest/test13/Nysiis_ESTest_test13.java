package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test13 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that {@link Nysiis#encode(String)} ignores digits and punctuation
     * (which the cleaning step strips) and encodes only the letters, in strict mode
     * the result is capped at six characters.
     */
    @Test(timeout = 4000)
    public void encodeStripsNonLettersBeforeEncoding() throws Throwable {
        Nysiis nysiis = new Nysiis();

        String encoded = nysiis.encode("@A9Q mhK(EVj%r");

        assertEquals("AGNCAF", encoded);
    }
}
