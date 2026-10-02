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
public class FileTimes_ESTest_test14 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that converting a FileTime to NTFS time adds the Windows-to-Unix epoch offset.
     *
     * <p>An NTFS time counts 100-nanosecond intervals since 1601-01-01, while a FileTime is
     * measured from the Unix epoch (1970-01-01). The constant offset between these two epochs
     * is 116_444_736_000_000_000 (in 100-nanosecond units).</p>
     *
     * <p>Here the FileTime is 1070 nanoseconds after the Unix epoch, which equals 10 whole
     * 100-nanosecond intervals (the remaining 70 nanoseconds are truncated). Adding the epoch
     * offset therefore yields 116_444_736_000_000_010.</p>
     */
    @Test(timeout = 4000)
    public void testToNtfsTimeAddsEpochOffsetToFileTime() throws Throwable {
        FileTime fileTimeJustAfterUnixEpoch = FileTime.from(1070L, TimeUnit.NANOSECONDS);

        long ntfsTime = FileTimes.toNtfsTime(fileTimeJustAfterUnixEpoch);

        assertEquals(116444736000000010L, ntfsTime);
    }
}
