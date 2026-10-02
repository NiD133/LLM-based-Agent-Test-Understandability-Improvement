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

/**
 * Verifies that an X5455 extended-timestamp extra field survives a write-then-read
 * round trip through a ZIP archive: the modify time we store before writing must be
 * the modify time we read back afterwards.
 */
public class X5455_ExtendedTimestampTest_testWriteReadRoundtrip {

    /** Header ID ("UT") used to look the extra field back up after reading. */
    private static final ZipShort X5455 = new ZipShort(0x5455);

    /** The extended-timestamp extra field under test. */
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
        final File zipFile = new File(tmpDir, "write_rewrite.zip");

        // Build a fixed, time-zone-independent modify time (1997-09-24 15:10:02).
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1997, 8, 24, 15, 10, 2);
        final Date modifyTime = calendar.getTime();

        // Write a single entry "foo" carrying the extra field with only the modify time present.
        try (OutputStream out = Files.newOutputStream(zipFile.toPath());
                ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(out)) {
            final ZipArchiveEntry entry = new ZipArchiveEntry("foo");
            xf.setModifyJavaTime(modifyTime);
            xf.setFlags((byte) 1);
            entry.addExtraField(xf);
            zipOut.putArchiveEntry(entry);
            zipOut.closeArchiveEntry();
        }

        // Read the archive back and confirm the extra field reports the same modify time.
        try (ZipFile readZip = ZipFile.builder().setFile(zipFile).get()) {
            final ZipArchiveEntry entry = readZip.getEntry("foo");
            final X5455_ExtendedTimestamp readField = (X5455_ExtendedTimestamp) entry.getExtraField(X5455);
            assertNotNull(readField);
            assertTrue(readField.isBit0_modifyTimePresent());
            assertEquals(modifyTime, readField.getModifyJavaTime());
        }
    }
}
