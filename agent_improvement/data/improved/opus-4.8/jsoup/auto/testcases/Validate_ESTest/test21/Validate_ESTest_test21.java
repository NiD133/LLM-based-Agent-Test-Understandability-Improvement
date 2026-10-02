package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test21 extends Validate_ESTest_scaffolding {

    /**
     * When given a non-null value, expectNotNull should return that same value unchanged.
     */
    @Test(timeout = 4000)
    public void expectNotNullReturnsSameNonNullValue() throws Throwable {
        Object returned = Validate.expectNotNull((Object) "di");

        assertEquals("di", returned);
    }
}
