package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test23 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that equals() is reflexive: an X5455_ExtendedTimestamp instance
     * must be equal to itself, as required by the Object.equals() contract.
     */
    @Test(timeout = 4000)
    public void test23_equalsIsReflexive() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();
        boolean isEqualToItself = timestamp.equals(timestamp);
        assertTrue("An X5455_ExtendedTimestamp instance must be equal to itself", isEqualToItself);
    }
}
