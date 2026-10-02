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
public class FileTimes_ESTest_test09 extends FileTimes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Convert a small negative NTFS timestamp to a FileTime (near the NTFS epoch)
        FileTime originalFileTime = FileTimes.ntfsTimeToFileTime(-3078L);

        // Shift the FileTime backward by the Unix-to-NTFS epoch offset (expressed as nanoseconds).
        // FileTimes.UNIX_TO_NTFS_OFFSET is -116444736000000000L (100-ns intervals), so multiplied
        // by 100 gives the nanosecond equivalent; here we pass the raw offset value directly as
        // nanoseconds, which moves the instant further into the past.
        FileTime shiftedFileTime = FileTimes.plusNanos(originalFileTime, FileTimes.UNIX_TO_NTFS_OFFSET);

        // The shifted time must differ from the original because the offset is non-zero
        assertFalse(shiftedFileTime.equals((Object) originalFileTime));
    }
}
