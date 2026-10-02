package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test01 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#checkStartsWith(String, String)} returns true
     * when a string is compared against itself: any string trivially starts with
     * its own full value, regardless of case sensitivity.
     */
    @Test(timeout = 4000)
    public void checkStartsWith_whenStringStartsWithItself_returnsTrue() throws Throwable {
        IOCase caseInsensitive = IOCase.INSENSITIVE;
        String text = "org.apache.commons.io.Filena+eUtils";

        boolean startsWithItself = caseInsensitive.checkStartsWith(text, text);

        assertTrue(startsWithItself);
    }
}
