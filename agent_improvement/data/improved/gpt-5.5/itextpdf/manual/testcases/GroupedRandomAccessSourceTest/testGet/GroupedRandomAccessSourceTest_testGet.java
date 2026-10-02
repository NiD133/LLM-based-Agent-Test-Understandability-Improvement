package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupedRandomAccessSourceTest_testGet {

    private static final int SOURCE_SIZE = 100;
    private static final int FIRST_SOURCE_LAST_INDEX = SOURCE_SIZE - 1;
    private static final int SECOND_SOURCE_FIRST_INDEX = SOURCE_SIZE;
    private static final int SECOND_SOURCE_SECOND_INDEX = SOURCE_SIZE + 1;
    private static final int THIRD_SOURCE_LAST_INDEX = (SOURCE_SIZE * 3) - 1;
    private static final int FIRST_INDEX_AFTER_GROUP = SOURCE_SIZE * 3;

    private byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < SOURCE_SIZE; i++) {
            baos.write((byte) i);
        }
        data = baos.toByteArray();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);
        RandomAccessSource[] inputs = new RandomAccessSource[] { source1, source2, source3 };
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(inputs);

        Assert.assertEquals(source1.length() + source2.length() + source3.length(), grouped.length());
        Assert.assertEquals(source1.get(99), grouped.get(FIRST_SOURCE_LAST_INDEX));
        Assert.assertEquals(source2.get(0), grouped.get(SECOND_SOURCE_FIRST_INDEX));
        Assert.assertEquals(source2.get(1), grouped.get(SECOND_SOURCE_SECOND_INDEX));
        Assert.assertEquals(source1.get(99), grouped.get(FIRST_SOURCE_LAST_INDEX));
        Assert.assertEquals(source3.get(99), grouped.get(THIRD_SOURCE_LAST_INDEX));
        Assert.assertEquals(-1, grouped.get(FIRST_INDEX_AFTER_GROUP));
    }
}
