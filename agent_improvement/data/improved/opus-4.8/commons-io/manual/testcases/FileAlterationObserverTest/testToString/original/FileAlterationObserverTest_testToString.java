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

public class FileAlterationObserverTest_testToString extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Test toString().
     */
    @Test
    void testToString() {
        final File file = new File(PATH_STRING_FIXTURE);
        final Builder builder = FileAlterationObserver.builder();
        FileAlterationObserver observer = builder.setFile(file).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', true, listeners=0]", observer.toString());
        observer = builder.setFileFilter(CanReadFileFilter.CAN_READ).getUnchecked();
        assertEquals("FileAlterationObserver[file='" + file.getPath() + "', CanReadFileFilter, listeners=0]", observer.toString());
        assertEquals(file, observer.getDirectory());
    }
}
