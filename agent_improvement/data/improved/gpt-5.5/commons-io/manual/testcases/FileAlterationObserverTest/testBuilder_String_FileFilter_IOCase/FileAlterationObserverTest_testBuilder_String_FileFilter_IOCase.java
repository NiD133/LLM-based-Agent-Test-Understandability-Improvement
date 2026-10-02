package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_String_FileFilter_IOCase extends AbstractMonitorTest {

    private static final String DIRECTORY_PATH = "/foo";

    private String toUnixPath(final File directory) {
        return FilenameUtils.separatorsToUnix(directory.toString());
    }

    @Test
    void testBuilder_String_FileFilter_IOCase() {
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(DIRECTORY_PATH)
                .setFileFilter(CanReadFileFilter.CAN_READ)
                .setIOCase(IOCase.INSENSITIVE)
                .getUnchecked();

        assertEquals(DIRECTORY_PATH, toUnixPath(observer.getDirectory()));
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
        assertEquals(NameFileComparator.NAME_INSENSITIVE_COMPARATOR, observer.getComparator());
    }
}
