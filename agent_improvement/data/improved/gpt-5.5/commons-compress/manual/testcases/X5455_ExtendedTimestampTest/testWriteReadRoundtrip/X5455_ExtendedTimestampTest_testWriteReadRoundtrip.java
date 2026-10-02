package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.compress.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class X5455_ExtendedTimestampTest_testWriteReadRoundtrip {

    private static final ZipShort X5455 = new ZipShort(0x5455);
    private static final String ARCHIVE_NAME = "write_rewrite.zip";
    private static final String ENTRY_NAME = "foo";
    private static final byte MODIFY_TIME_ONLY = 1;

    /**
     * The extended timestamp field added to the archive entry under test.
     */
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
    void testWriteReadRoundtrip() throws IOException {
        final File output = new File(tmpDir, ARCHIVE_NAME);
        final Date date = dateOf(1997, 8, 24, 15, 10, 2);

        writeArchiveWithModifyTime(output, date);

        try (ZipFile zf = ZipFile.builder().setFile(output).get()) {
            final ZipArchiveEntry ze = zf.getEntry(ENTRY_NAME);
            final X5455_ExtendedTimestamp ext = (X5455_ExtendedTimestamp) ze.getExtraField(X5455);
            assertNotNull(ext);
            assertTrue(ext.isBit0_modifyTimePresent());
            assertEquals(date, ext.getModifyJavaTime());
        }
    }

    private Date dateOf(final int year, final int month, final int day, final int hour, final int minute, final int second) {
        final Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(year, month, day, hour, minute, second);
        return instance.getTime();
    }

    private void writeArchiveWithModifyTime(final File output, final Date date) throws IOException {
        try (OutputStream out = Files.newOutputStream(output.toPath());
            ZipArchiveOutputStream os = new ZipArchiveOutputStream(out)) {
            final ZipArchiveEntry ze = new ZipArchiveEntry(ENTRY_NAME);
            xf.setModifyJavaTime(date);
            xf.setFlags(MODIFY_TIME_ONLY);
            ze.addExtraField(xf);
            os.putArchiveEntry(ze);
            os.closeArchiveEntry();
        }
    }
}
