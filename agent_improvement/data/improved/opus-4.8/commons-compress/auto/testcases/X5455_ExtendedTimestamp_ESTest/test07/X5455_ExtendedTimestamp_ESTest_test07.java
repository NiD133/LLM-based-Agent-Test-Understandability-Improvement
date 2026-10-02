package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test07 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the modify time to a null Date must leave the modify-time bit unset,
     * so the flags byte stays at its default of 0.
     */
    @Test(timeout = 4000)
    public void setModifyJavaTimeToNullKeepsModifyBitUnset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setModifyJavaTime((Date) null);

        assertEquals((byte) 0, extendedTimestamp.getFlags());
        assertFalse(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
