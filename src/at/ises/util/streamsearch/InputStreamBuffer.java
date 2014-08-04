//  Copyright 2014 @gitagon. For alternative licenses contact the author.
//
//  This file is part of streamsearch-kmp.
//  streamsearch-kmp is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Affero General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  streamsearch-kmp is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Affero General Public License for more details.
//  You should have received a copy of the GNU Affero General Public License
//  along with streamsearch-kmp.  If not, see <http://www.gnu.org/licenses/>.


package at.ises.util.streamsearch;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of {@link StreamBuffer}. 
 * Note: for a maximum buffer size of 2^31 bytes (= 2 GiB).
 * @author @gitagon
 *
 */
public class InputStreamBuffer implements StreamBuffer
{
	
	final InputStream input;
	int increment = 2048;
	int size = 0; // size of buffer contents
	byte[] buffer; // buffer[0] = start of contents always


	public InputStreamBuffer(InputStream input, int increment) 
	{
		super();
		this.input = input;
		if(increment > 0) this.increment = increment;
		buffer = new byte[this.increment];
	}
	
	
	public byte[] getBuffer() { return buffer; }
	
	public int size() { return size; }
	
	
	/**
	 * Reads from the input stream.
	 * @return the number of bytes read (at least one) or a negative number 
	 * 		to indicate the stream is at end of file 
	 * 		or this object is in an inconsistent buffer state.
	 */
	public int read()
	{
		// assert space left in buffer
		int rem = buffer.length - size;
		if(rem == 0) increaseBuffer();
		else if (rem < 0) return -1; // inconsistent buffer state
		
		int result = -1;
		try {
			result = input.read(buffer, size, rem);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (result > 0) size += result;
		return result;
	}


	private void increaseBuffer() 
	{
		byte[] incBuf = new byte[buffer.length + increment]; 
		System.arraycopy(buffer, 0, incBuf, 0, size);
		buffer = incBuf;
	}
	
	

}
