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

/**
 * 
 * @author @gitagon
 *
 */
public interface StreamBuffer {

	/** Returns the buffer array (possibly just a shallow reference). */
	public byte[] getBuffer();
	
	/** Size of buffer contents (not the buffer array length). */
	public int size();
	
	/**
	 * Reads from the input stream into the buffer array.
	 * @return the number of bytes read (at least one) or a negative number 
	 * 		to indicate the stream is at end of file 
	 * 		or this object is in an inconsistent buffer state.
	 */
	public int read();

}
