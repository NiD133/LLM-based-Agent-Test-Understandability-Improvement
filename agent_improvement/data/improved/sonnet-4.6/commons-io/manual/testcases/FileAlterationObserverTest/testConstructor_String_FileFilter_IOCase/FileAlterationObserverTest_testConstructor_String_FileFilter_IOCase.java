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

    private static final String DIRECTORY_PATH = "/foo";

    private String getDirectoryAsUnixPath(final FileAlterationObserver observer) {
        return FilenameUtils.separatorsToUnix(observer.getDirectory().toString());
    }

    @Test
    void testConstructor_String_FileFilter_IOCase() {
        // Construct observer using the deprecated (String, FileFilter, IOCase) constructor
        @SuppressWarnings("deprecation")
        final FileAlterationObserver observer = new FileAlterationObserver(DIRECTORY_PATH, CanReadFileFilter.CAN_READ, IOCase.INSENSITIVE);

        // Verify the observed directory path was stored correctly
        assertEquals(DIRECTORY_PATH, getDirectoryAsUnixPath(observer));

        // Verify the file filter was stored as-is
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());

        // IOCase.INSENSITIVE should map to the case-insensitive name comparator
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
