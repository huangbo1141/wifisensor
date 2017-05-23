package robin.com.wifisensor.util.exif;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferInputStream extends InputStream {

    private ByteBuffer mBuf;

    public ByteBufferInputStream(ByteBuffer buf) {
        mBuf = buf;
    }

    @Override
    public int read() {
        if (!mBuf.hasRemaining()) {
            return -1;
        }
        return mBuf.get() & 0xFF;
    }

    @Override
    public int read(byte[] bytes, int off, int len) {
        if (!mBuf.hasRemaining()) {
            return -1;
        }

        len = Math.min(len, mBuf.remaining());
        mBuf.get(bytes, off, len);
        return len;
    }
}
