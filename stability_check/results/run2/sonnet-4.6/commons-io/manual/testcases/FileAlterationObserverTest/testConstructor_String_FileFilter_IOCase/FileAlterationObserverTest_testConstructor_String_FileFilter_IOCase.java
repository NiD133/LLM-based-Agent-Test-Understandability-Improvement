package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationObserver.Builder;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testConstructor_String_FileFilter_IOCase extends AbstractMonitorTest {

    // Unix-style path used as a simple directory name input (does not need to exist on disk)
    private static final String PATH_STRING_FIXTURE = "/foo";

    private String directoryToUnixString(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    /**
     * Verifies that the deprecated String+FileFilter+IOCase constructor correctly wires:
     * - the directory path from the String argument,
     * - the file filter from the FileFilter argument, and
     * - a case-insensitive file comparator when IOCase.INSENSITIVE is supplied.
     */
    @Test
    void testConstructor_String_FileFilter_IOCase() {
        // Arrange
        final String directoryPath = PATH_STRING_FIXTURE;
        final FileFilter readableFilesOnly = CanReadFileFilter.CAN_READ;
        final IOCase caseInsensitive = IOCase.INSENSITIVE;

        // Act — deprecated constructor under test
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(directoryPath, readableFilesOnly, caseInsensitive);

        // Assert: directory path is stored as supplied
        assertEquals(directoryPath, directoryToUnixString(observer));

        // Assert: the file filter is stored as supplied
        assertEquals(readableFilesOnly, observer.getFileFilter());

        // Assert: IOCase.INSENSITIVE maps to the case-insensitive name comparator
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
