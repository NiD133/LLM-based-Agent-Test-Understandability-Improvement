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
public class FileTimes_ESTest_test04 extends FileTimes_ESTest_scaffolding {

    // The NTFS-to-Unix epoch offset in 100-nanosecond units, reused here as a
    // millisecond timestamp to produce a far-future date (year 3687943).
    private static final long NTFS_UNIX_OFFSET_AS_MILLIS = -116444736000000000L;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: a Date constructed from the NTFS-to-Unix offset value used as milliseconds
        MockDate inputDate = new MockDate(NTFS_UNIX_OFFSET_AS_MILLIS);

        // Act: convert Date → FileTime → Date (round-trip)
        FileTime fileTime = FileTimes.toFileTime(inputDate);
        Date roundTrippedDate = FileTimes.toDate(fileTime);

        // Assert: the round-tripped date represents the expected far-future point in time
        assertEquals("Sat Dec 12 00:00:00 GMT 3687943", roundTrippedDate.toString());
    }
}
