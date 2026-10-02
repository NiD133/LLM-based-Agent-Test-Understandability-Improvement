package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test07 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that {@link Nysiis#nysiis(String)} ignores non-letter characters
     * before encoding: the punctuation and digits in "B*(EG$;*A+w7oQ" are stripped
     * during cleaning, leaving "BEGAWOQ", which the NYSIIS algorithm encodes as "BAGAG".
     * Also confirms the default constructor produces a strict-mode encoder.
     */
    @Test(timeout = 4000)
    public void encodesInputWithNonLetterCharactersAndUsesStrictModeByDefault() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        String encoded = defaultEncoder.nysiis("B*(EG$;*A+w7oQ");

        assertEquals("BAGAG", encoded);
        assertTrue("default constructor should enable strict mode", defaultEncoder.isStrict());
    }
}
