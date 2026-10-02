package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QCodecTest_testLetUsMakeCloverHappy {

    @Test
    void testLetUsMakeCloverHappy() throws Exception {
        final QCodec qcodec = new QCodec();

        qcodec.setEncodeBlanks(true);
        assertTrue(qcodec.isEncodeBlanks());

        qcodec.setEncodeBlanks(false);
        assertFalse(qcodec.isEncodeBlanks());
    }
}
