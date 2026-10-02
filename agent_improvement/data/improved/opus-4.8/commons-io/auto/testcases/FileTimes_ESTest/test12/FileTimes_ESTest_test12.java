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
public class FileTimes_ESTest_test12 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#ntfsTimeToDate(long)} converts an NTFS time
     * (100-nanosecond intervals since 1601-01-01 UTC) into the matching {@link Date}.
     *
     * <p>The chosen NTFS time sits slightly before the Unix epoch, so the resulting
     * date falls in December 1231.</p>
     */
    @Test(timeout = 4000)
    public void testNtfsTimeToDateBeforeUnixEpoch() throws Throwable {
        final long ntfsTimeBeforeUnixEpoch = -116444736000000003L;

        final Date convertedDate = FileTimes.ntfsTimeToDate(ntfsTimeBeforeUnixEpoch);

        assertEquals("Thu Dec 25 23:59:59 GMT 1231", convertedDate.toString());
    }
}
