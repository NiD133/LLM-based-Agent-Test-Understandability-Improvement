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
     * Verifies that encoding a String via the Object-based {@link Nysiis#encode(Object)}
     * entry point produces the expected 6-character NYSIIS code.
     * In strict mode (the default) the code is truncated to a maximum length of 6.
     */
    @Test(timeout = 4000)
    public void encodeStringObjectReturnsTruncatedNysiisCode() throws Throwable {
        Nysiis nysiis = new Nysiis();

        Object encoded = nysiis.encode((Object) "org.apache.commons.codec.EncodeyException");

        assertNotNull(encoded);
        assertEquals("ORGAPA", encoded);
    }
}
