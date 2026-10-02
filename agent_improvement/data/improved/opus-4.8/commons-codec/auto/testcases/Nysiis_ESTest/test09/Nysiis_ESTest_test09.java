package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test09 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that, in the default strict mode, {@link Nysiis#nysiis(String)}
     * strips non-letter characters and digits from the input, then encodes the
     * remaining letters and caps the result at 6 characters.
     */
    @Test(timeout = 4000)
    public void nysiisIgnoresDigitsAndSymbolsAndCapsLengthAtSix() throws Throwable {
        Nysiis nysiis = new Nysiis();

        String encoded = nysiis.nysiis("dIpYze0FuZfr/K1EPh%");

        assertEquals("DAPYSA", encoded);
    }
}
