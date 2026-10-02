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
public class X5455_ExtendedTimestamp_ESTest_test16 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that retrieving the local file data does not alter the flags byte.
     *
     * When only CREATE_TIME_BIT (bit 2) is set and no create timestamp has been
     * assigned, getLocalFileDataData() should produce a one-byte payload with the
     * flags byte zeroed out (create time is absent), while the flags field on the
     * object itself must remain unchanged.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Set only the create-time bit; no actual timestamp value is provided.
        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.CREATE_TIME_BIT);

        // Retrieve the local file data to exercise the serialisation path.
        // Because createTime is null, the returned payload will be a single zeroed byte.
        byte[] localFileData = extendedTimestamp.getLocalFileDataData();

        // The flags field on the object must be preserved exactly as set.
        assertEquals(X5455_ExtendedTimestamp.CREATE_TIME_BIT, extendedTimestamp.getFlags());
    }
}
