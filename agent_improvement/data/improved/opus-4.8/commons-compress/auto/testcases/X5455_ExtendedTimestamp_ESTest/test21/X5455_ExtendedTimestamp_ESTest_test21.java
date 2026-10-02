package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test21 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Two fields can carry the same flags byte yet still be unequal when their
     * timestamp values differ. Here one field only has the access-time bit set
     * (but no actual access time), while the other has a real access time set
     * (which implicitly turns on the same bit). The flags match, but the access
     * timestamps do not, so {@code equals} must report them as different.
     */
    @Test(timeout = 4000)
    public void accessTimeBitWithoutTimestampDiffersFromOneWithTimestamp() throws Throwable {
        X5455_ExtendedTimestamp fieldWithAccessTime = new X5455_ExtendedTimestamp();
        X5455_ExtendedTimestamp fieldWithBitOnly = new X5455_ExtendedTimestamp();

        // Only flip the access-time flag bit; no access timestamp is stored.
        fieldWithBitOnly.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);

        // Store an actual access time; this also sets the access-time flag bit.
        FileTime accessTime = FileTime.fromMillis(4L);
        fieldWithAccessTime.setAccessFileTime(accessTime);

        boolean areEqual = fieldWithBitOnly.equals(fieldWithAccessTime);

        // Setting the access file time switched on the access-time bit (value 2).
        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, fieldWithAccessTime.getFlags());
        // Same flags, but one has a stored access time and the other does not.
        assertFalse(areEqual);
    }
}
