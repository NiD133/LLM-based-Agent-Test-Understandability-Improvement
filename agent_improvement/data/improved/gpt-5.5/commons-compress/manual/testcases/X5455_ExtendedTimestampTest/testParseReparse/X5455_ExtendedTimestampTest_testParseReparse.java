package org.apache.commons.compress.archivers.zip;

import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.ACCESS_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.CREATE_TIME_BIT;
import static org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp.MODIFY_TIME_BIT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.zip.ZipException;

import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class X5455_ExtendedTimestampTest_testParseReparse {

    private static final ZipLong ZERO_TIME = new ZipLong(0);
    private static final ZipLong MAX_TIME_SECONDS = new ZipLong(Integer.MAX_VALUE);

    private static final byte NO_TIMESTAMP_FLAGS = 0;
    private static final byte MODIFY_ACCESS_FLAGS = MODIFY_TIME_BIT | ACCESS_TIME_BIT;
    private static final byte ALL_TIMESTAMP_FLAGS = MODIFY_TIME_BIT | ACCESS_TIME_BIT | CREATE_TIME_BIT;

    private static final byte[] NO_TIMESTAMPS = { NO_TIMESTAMP_FLAGS };

    private static final byte[] CENTRAL_ACCESS_FLAG_ONLY = { ACCESS_TIME_BIT };
    private static final byte[] CENTRAL_CREATE_FLAG_ONLY = { CREATE_TIME_BIT };

    private static final byte[] MODIFY_TIME_ZERO = { MODIFY_TIME_BIT, 0, 0, 0, 0 };
    private static final byte[] MODIFY_TIME_MAX = { MODIFY_TIME_BIT, -1, -1, -1, 0x7f };

    private static final byte[] ACCESS_TIME_ZERO = { ACCESS_TIME_BIT, 0, 0, 0, 0 };
    private static final byte[] ACCESS_TIME_MAX = { ACCESS_TIME_BIT, -1, -1, -1, 0x7f };

    private static final byte[] CREATE_TIME_ZERO = { CREATE_TIME_BIT, 0, 0, 0, 0 };
    private static final byte[] CREATE_TIME_MAX = { CREATE_TIME_BIT, -1, -1, -1, 0x7f };

    private static final byte[] MODIFY_ACCESS_TIME_ZERO = { MODIFY_ACCESS_FLAGS, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final byte[] MODIFY_ACCESS_TIME_MAX = { MODIFY_ACCESS_FLAGS, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

    private static final byte[] ALL_TIMES_ZERO = { ALL_TIMESTAMP_FLAGS, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final byte[] ALL_TIMES_MAX = { ALL_TIMESTAMP_FLAGS, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f, -1, -1, -1, 0x7f };

    private X5455_ExtendedTimestamp xf;

    @TempDir
    private File tmpDir;

    @BeforeEach
    public void before() {
        xf = new X5455_ExtendedTimestamp();
    }

    @AfterEach
    public void removeTempFiles() {
        if (tmpDir != null) {
            AbstractTest.forceDelete(tmpDir);
        }
    }

    @Test
    void testParseReparse() throws ZipException {
        parseReparse(null, NO_TIMESTAMPS, NO_TIMESTAMPS);

        parseReparse(ZERO_TIME, MODIFY_TIME_ZERO, MODIFY_TIME_ZERO);
        parseReparse(MAX_TIME_SECONDS, MODIFY_TIME_MAX, MODIFY_TIME_MAX);

        parseReparse(ZERO_TIME, ACCESS_TIME_ZERO, CENTRAL_ACCESS_FLAG_ONLY);
        parseReparse(MAX_TIME_SECONDS, ACCESS_TIME_MAX, CENTRAL_ACCESS_FLAG_ONLY);

        parseReparse(ZERO_TIME, CREATE_TIME_ZERO, CENTRAL_CREATE_FLAG_ONLY);
        parseReparse(MAX_TIME_SECONDS, CREATE_TIME_MAX, CENTRAL_CREATE_FLAG_ONLY);

        parseReparse(ZERO_TIME, MODIFY_ACCESS_TIME_ZERO, MODIFY_TIME_ZERO);
        parseReparse(MAX_TIME_SECONDS, MODIFY_ACCESS_TIME_MAX, MODIFY_TIME_MAX);

        parseReparse(ZERO_TIME, ALL_TIMES_ZERO, MODIFY_TIME_ZERO);
        parseReparse(MAX_TIME_SECONDS, ALL_TIMES_MAX, MODIFY_TIME_MAX);

        assertSpuriousFlagBitsAreDiscarded((byte) 15);
        assertSpuriousFlagBitsAreDiscarded((byte) 31);
        assertSpuriousFlagBitsAreDiscarded((byte) 63);
        assertSpuriousFlagBitsAreDiscarded((byte) 71);
        assertSpuriousFlagBitsAreDiscarded((byte) 127);
        assertSpuriousFlagBitsAreDiscarded((byte) -1);
    }

    private void assertSpuriousFlagBitsAreDiscarded(final byte providedFlags) throws ZipException {
        parseReparse(providedFlags, MAX_TIME_SECONDS, ALL_TIMESTAMP_FLAGS, ALL_TIMES_MAX, MODIFY_TIME_MAX);
    }

    private void parseReparse(final ZipLong time, final byte[] expectedLocal, final byte[] almostExpectedCentral) throws ZipException {
        parseReparse(expectedLocal[0], time, expectedLocal[0], expectedLocal, almostExpectedCentral);
    }

    private void parseReparse(final byte providedFlags, final ZipLong time, final byte expectedFlags, final byte[] expectedLocal,
            final byte[] almostExpectedCentral) throws ZipException {
        final byte[] expectedCentral = expectedCentralData(almostExpectedCentral, expectedFlags);

        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);

        byte[] result = xf.getLocalFileDataData();
        assertArrayEquals(expectedLocal, result);

        xf.parseFromLocalFileData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        assertLocalTimestampFieldsWereParsed(expectedFlags, time);

        xf.setModifyTime(time);
        xf.setAccessTime(time);
        xf.setCreateTime(time);
        xf.setFlags(providedFlags);

        result = xf.getCentralDirectoryData();
        assertArrayEquals(expectedCentral, result);

        xf.parseFromCentralDirectoryData(result, 0, result.length);
        assertEquals(expectedFlags, xf.getFlags());
        assertCentralDirectoryTimestampFieldsWereParsed(expectedFlags, time);
    }

    private byte[] expectedCentralData(final byte[] almostExpectedCentral, final byte expectedFlags) {
        final byte[] expectedCentral = new byte[almostExpectedCentral.length];
        System.arraycopy(almostExpectedCentral, 0, expectedCentral, 0, almostExpectedCentral.length);
        expectedCentral[0] = expectedFlags;
        return expectedCentral;
    }

    private void assertLocalTimestampFieldsWereParsed(final byte expectedFlags, final ZipLong time) {
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
        if (isFlagSet(expectedFlags, ACCESS_TIME_BIT)) {
            assertTrue(xf.isBit1_accessTimePresent());
            assertEquals(time, xf.getAccessTime());
        }
        if (isFlagSet(expectedFlags, CREATE_TIME_BIT)) {
            assertTrue(xf.isBit2_createTimePresent());
            assertEquals(time, xf.getCreateTime());
        }
    }

    private void assertCentralDirectoryTimestampFieldsWereParsed(final byte expectedFlags, final ZipLong time) {
        if (isFlagSet(expectedFlags, MODIFY_TIME_BIT)) {
            assertTrue(xf.isBit0_modifyTimePresent());
            assertEquals(time, xf.getModifyTime());
        }
    }

    private boolean isFlagSet(final byte data, final byte flag) {
        return (data & flag) == flag;
    }
}
