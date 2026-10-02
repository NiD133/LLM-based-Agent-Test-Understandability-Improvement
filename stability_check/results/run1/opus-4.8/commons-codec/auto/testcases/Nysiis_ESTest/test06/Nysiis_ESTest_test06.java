package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test06 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that encoding a String via the Object-based {@link Nysiis#encode(Object)}
     * method returns the expected NYSIIS code. In strict mode (the default) the code is
     * capped at 6 characters, so the input is encoded and then truncated to "ORGAPA".
     */
    @Test(timeout = 4000)
    public void encodeStringObjectReturnsSixCharacterNysiisCode() throws Throwable {
        Nysiis nysiis = new Nysiis();

        Object encoded = nysiis.encode((Object) "org.apache.commons.codec.EncodeyException");

        assertNotNull(encoded);
        assertEquals("ORGAPA", encoded);
    }
}
