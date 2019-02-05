/*
 * junitflood - An automatic junit test generator
 * Copyright 2011-2019 MeBigFatGuy.com
 * Copyright 2011-2019 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.junitflood.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LengthLimitedInputStream extends FilterInputStream {

	private long streamLength;

	public LengthLimitedInputStream(InputStream src, long length) {
		super(src);
		streamLength = length;
	}

    @Override
    public int read() throws IOException {
        if (streamLength > 0) {
            int b = super.read();
            streamLength--;
            return b;
        }

        return -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (streamLength <= 0) {
            return -1;
        }

        if (len > streamLength) {
            len = (int)streamLength;
        }

        int bytes = super.read(b, off, len);
        streamLength -= bytes;
        return bytes;
    }

    @Override
    public long skip(long n) throws IOException {

        if (n > streamLength) {
            n = streamLength;
        }

        long bytes = super.skip(n);
        streamLength -= bytes;
        return bytes;
    }

    @Override
    public int available() throws IOException {
        int bytes = super.available();
        if (bytes > streamLength) {
            bytes = (int)streamLength;
        }

        return bytes;
    }

    @Override
    public synchronized void mark(int readlimit) {
    	throw new UnsupportedOperationException("mark not supported");
    }

    @Override
    public synchronized void reset() throws IOException
    {
        throw new UnsupportedOperationException("reset not supported");
    }

    @Override
    public boolean markSupported()
    {
        return false;
    }
}
