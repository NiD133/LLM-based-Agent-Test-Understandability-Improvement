package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test01 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Instant mockedCurrentInstant = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedCurrentInstant);

        String formattedUtcInstant = utcInstant.toString();

        assertNotNull(formattedUtcInstant);
        assertEquals("2014-02-14T20:21:21.320Z", formattedUtcInstant);
    }
}
