package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test07 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Generating a string of zero code points should yield an empty string,
     * regardless of the generator's configuration.
     */
    @Test(timeout = 4000)
    public void generateZeroLengthReturnsEmptyString() throws Throwable {
        RandomStringGenerator generator = RandomStringGenerator.builder().get();

        String generated = generator.generate(0);

        assertEquals("", generated);
    }
}
