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

    /**
     * Encoding an arbitrary String via the Object-based {@code encode} method should
     * return the NYSIIS code, truncated to the default strict-mode maximum of 6 characters.
     */
    @Test(timeout = 4000)
    public void encodeStringReturnsSixCharacterNysiisCode() throws Throwable {
        Nysiis nysiis = new Nysiis();

        Object encoded = nysiis.encode((Object) "org.apache.commons.codec.EncodeyException");

        assertNotNull(encoded);
        assertEquals("ORGAPA", encoded);
    }
}
