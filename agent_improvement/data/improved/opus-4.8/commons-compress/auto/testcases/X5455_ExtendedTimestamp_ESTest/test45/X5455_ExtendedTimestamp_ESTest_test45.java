package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.attribute.FileTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test45 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A newly constructed extended-timestamp field has no access time set,
     * so {@link X5455_ExtendedTimestamp#getAccessFileTime()} should return null.
     */
    @Test(timeout = 4000)
    public void getAccessFileTimeIsNullWhenNoAccessTimeSet() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        FileTime accessFileTime = extendedTimestamp.getAccessFileTime();

        assertNull("Access file time should be null when none has been set", accessFileTime);
    }
}
