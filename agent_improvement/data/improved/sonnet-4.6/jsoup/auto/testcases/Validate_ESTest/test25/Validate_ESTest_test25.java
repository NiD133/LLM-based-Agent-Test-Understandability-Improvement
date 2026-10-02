package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test25 extends Validate_ESTest_scaffolding {

    // Verifies that ensureNotNull returns the original non-null object unchanged
    @Test(timeout = 4000)
    public void test25_ensureNotNull_returnsNonNullObjectUnchanged() throws Throwable {
        Integer negativeValue = new Integer((-1));
        Object result = Validate.ensureNotNull((Object) negativeValue);
        assertEquals((-1), result);
    }
}
