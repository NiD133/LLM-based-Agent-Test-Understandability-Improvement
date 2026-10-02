package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test23 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that converting a null UtcInstant to TaiInstant throws NullPointerException.
     * The NPE originates in UtcRules when it attempts to dereference the null argument.
     */
    @Test(timeout = 4000)
    public void test_ofUtcInstant_nullArgument_throwsNullPointerException() throws Throwable {
        try {
            TaiInstant.of((UtcInstant) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.threeten.extra.scale.UtcRules", e);
        }
    }
}
