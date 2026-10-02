package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test05 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the flags byte to a value whose bit 0 is set (0x55 = binary 0101_0101)
     * should mark the modify-time field as present. {@code toString()} is exercised
     * to confirm it runs without error for this flag configuration.
     */
    @Test(timeout = 4000)
    public void flagsWithBit0Set_marksModifyTimePresent() throws Throwable {
        final byte flagsWithBit0Set = (byte) 85; // 0x55, bit 0 = modify-time present

        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        extendedTimestamp.setFlags(flagsWithBit0Set);
        extendedTimestamp.toString();

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
