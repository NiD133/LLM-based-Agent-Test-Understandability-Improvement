package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test07 extends JavaVersion_ESTest_scaffolding {

    /**
     * The string "0" does not match any known Java version constant, so
     * {@link JavaVersion#get(String)} falls through to its parsing branch.
     * This exercises that path without throwing an exception.
     */
    @Test(timeout = 4000)
    public void getWithUnknownVersionStringDoesNotThrow() throws Throwable {
        JavaVersion.get("0");
    }
}
