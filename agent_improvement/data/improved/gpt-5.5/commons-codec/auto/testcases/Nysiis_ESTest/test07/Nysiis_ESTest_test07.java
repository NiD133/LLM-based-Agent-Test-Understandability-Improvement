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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Nysiis strictEncoder = new Nysiis();

        // Non-letter characters are cleaned before the NYSIIS code is generated.
        String encodedName = strictEncoder.nysiis("B*(EG$;*A+w7oQ");

        assertEquals("BAGAG", encodedName);
        assertTrue(strictEncoder.isStrict());
    }
}
