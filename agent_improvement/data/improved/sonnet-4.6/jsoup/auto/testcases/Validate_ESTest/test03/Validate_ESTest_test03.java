package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test03 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03_notEmptyParam_nonEmptyString_doesNotThrow() throws Throwable {
        // Both arguments happen to be the same non-empty string.
        // notEmptyParam(string, paramName) validates that `string` is not null/empty;
        // since it is not, no ValidationException should be thrown.
        String nonEmptyString = "Array must not contain any null objects";
        String paramName = "Array must not contain any null objects";
        Validate.notEmptyParam(nonEmptyString, paramName);
    }
}
