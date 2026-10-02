package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test16 extends FileTimes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testMinusNanosWithNegativeValueProducesDifferentFileTime() throws Throwable {
        // Create a base FileTime from a negative Unix timestamp (before epoch)
        FileTime baseFileTime = FileTimes.fromUnixTime(-2021L);

        // Subtracting a negative number of nanoseconds effectively adds nanoseconds,
        // so the result should differ from the original FileTime
        FileTime adjustedFileTime = FileTimes.minusNanos(baseFileTime, -2021L);

        assertFalse("minusNanos with a negative value should shift the time, producing a different FileTime",
                adjustedFileTime.equals((Object) baseFileTime));
    }
}
