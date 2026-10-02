package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test01 extends UtcInstant_ESTest_scaffolding {

    // MockInstant.now() is deterministically fixed to 2014-02-14T20:21:21.320Z in EvoSuite's mocked environment.
    @Test(timeout = 4000)
    public void test01_toStringReturnsIso8601UtcRepresentation() throws Throwable {
        Instant mockedNow = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedNow);

        String isoString = utcInstant.toString();

        assertNotNull(isoString);
        assertEquals("2014-02-14T20:21:21.320Z", isoString);
    }
}
