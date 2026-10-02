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

public class FileAlterationObserverTest_testBuilder_File_FileFilter extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    @Test
    void testBuilder_File_FileFilter() {
        final File file = new File(PATH_STRING_FIXTURE);
        // @formatter:off
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(file).setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        // @formatter:on
        assertEquals(file, observer.getDirectory());
        assertEquals(CanReadFileFilter.CAN_READ, observer.getFileFilter());
    }
}
