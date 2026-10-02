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

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Integer integer0 = new Integer((-1));
        Object object0 = Validate.ensureNotNull((Object) integer0);
        assertEquals((-1), object0);
    }
}
