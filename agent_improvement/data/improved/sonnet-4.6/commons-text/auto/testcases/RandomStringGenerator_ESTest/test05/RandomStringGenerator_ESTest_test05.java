package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test05 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that a generator restricted to null characters ('\0') can produce
     * a string of random length within the given bounds without throwing.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // A char array whose elements are all null characters ('\0' = code point 0)
        char[] nullChars = new char[4];

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .selectFrom(nullChars)
                .get();

        // Length is chosen randomly between 0 and 168; result consists solely of '\0'
        String result = generator.generate(0, 168);
    }
}
