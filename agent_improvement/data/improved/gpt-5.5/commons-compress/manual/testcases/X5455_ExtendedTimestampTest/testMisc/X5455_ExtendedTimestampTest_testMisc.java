package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testMisc {

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testMisc() throws Exception {
        assertNotEquals(xf, new Object());
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertFalse(xf.toString().contains(" Modify:"));
        assertFalse(xf.toString().contains(" Access:"));
        assertFalse(xf.toString().contains(" Create:"));

        Object emptyTimestampClone = xf.clone();
        assertEquals(emptyTimestampClone.hashCode(), xf.hashCode());
        assertEquals(xf, emptyTimestampClone);

        xf.setModifyJavaTime(new Date(1111));
        xf.setAccessJavaTime(new Date(2222));
        xf.setCreateJavaTime(new Date(3333));
        xf.setFlags((byte) 7);

        assertNotEquals(xf, emptyTimestampClone);
        assertTrue(xf.toString().startsWith("0x5455 Zip Extra Field"));
        assertTrue(xf.toString().contains(" Modify:"));
        assertTrue(xf.toString().contains(" Access:"));
        assertTrue(xf.toString().contains(" Create:"));

        Object populatedTimestampClone = xf.clone();
        assertEquals(populatedTimestampClone.hashCode(), xf.hashCode());
        assertEquals(xf, populatedTimestampClone);
    }
}
