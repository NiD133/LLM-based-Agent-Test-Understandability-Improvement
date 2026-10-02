package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test20 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that {@link Validate#expectNotNull(Object)} rejects a null argument
     * by throwing a ValidationException (an IllegalArgumentException subtype).
     */
    @Test(timeout = 4000)
    public void expectNotNull_whenObjectIsNull_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.expectNotNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expectNotNull rejects null with the message "Object must not be null"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
