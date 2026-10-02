package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test00 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * The X5455 flags byte is read from the data buffer at the parse offset.
     * Its bit 0 ({@link X5455_ExtendedTimestamp#MODIFY_TIME_BIT}) signals that a
     * modify-time field follows, so parsing a buffer whose flags byte has bit 0
     * set must mark the modify time as present.
     */
    @Test(timeout = 4000)
    public void parseFlagsWithModifyTimeBitMarksModifyTimePresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Buffer layout: parsing starts at offset 1, where the flags byte lives.
        // Setting that byte to MODIFY_TIME_BIT (1) requests a modify timestamp;
        // the four bytes that follow stay zero, encoding the timestamp itself.
        final int flagsOffset = 1;
        byte[] localFileData = new byte[6];
        localFileData[flagsOffset] = X5455_ExtendedTimestamp.MODIFY_TIME_BIT;

        extendedTimestamp.parseFromLocalFileData(localFileData, flagsOffset, 22);

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
