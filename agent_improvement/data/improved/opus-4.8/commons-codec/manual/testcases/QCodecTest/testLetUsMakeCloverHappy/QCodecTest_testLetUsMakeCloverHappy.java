package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec#setEncodeBlanks(boolean)} and
 * {@link QCodec#isEncodeBlanks()} act as a simple getter/setter pair:
 * whatever value is set is the value that is read back.
 */
public class QCodecTest_testLetUsMakeCloverHappy {

    @Test
    void isEncodeBlanksReflectsTheValuePreviouslySet() throws Exception {
        final QCodec qcodec = new QCodec();

        qcodec.setEncodeBlanks(true);
        assertTrue(qcodec.isEncodeBlanks(), "isEncodeBlanks should return true after setEncodeBlanks(true)");

        qcodec.setEncodeBlanks(false);
        assertFalse(qcodec.isEncodeBlanks(), "isEncodeBlanks should return false after setEncodeBlanks(false)");
    }
}
