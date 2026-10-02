package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test19 extends Validate_ESTest_scaffolding {

    /**
     * When the inspected object is non-null, expectNotNull returns that very same
     * object unchanged. The message and format arguments are only consulted to build
     * an exception when the object is null, so here they have no effect on the result.
     */
    @Test(timeout = 4000)
    public void expectNotNullReturnsSameObjectWhenNotNull() throws Throwable {
        Object nonNullInput = new Object();
        Object[] formatArgs = new Object[8];

        Object returned = Validate.expectNotNull(
                nonNullInput, "Array must not contain any null objects", formatArgs);

        assertSame(nonNullInput, returned);
    }
}
