package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.attribute.FileTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test08 extends FileTimes_ESTest_scaffolding {

    // Millisecond value corresponding to the NTFS epoch (January 1, 1601),
    // which is far before the Unix epoch and well outside the 32-bit Unix time range.
    private static final long NTFS_EPOCH_MILLIS = -116444736000000000L;

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MockDate ntfsEpochDate = new MockDate(NTFS_EPOCH_MILLIS);
        FileTime fileTimeAtNtfsEpoch = FileTimes.toFileTime(ntfsEpochDate);

        // A date from the NTFS epoch (year 1601) cannot fit in a 32-bit Unix timestamp
        boolean isRepresentableAsUnixTime = FileTimes.isUnixTime(fileTimeAtNtfsEpoch);

        assertFalse(isRepresentableAsUnixTime);
    }
}
