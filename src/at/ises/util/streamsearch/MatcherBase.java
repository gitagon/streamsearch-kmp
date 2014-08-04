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
public abstract class MatcherBase implements StreamBufferMatcher {

	protected final byte[] pat;
	protected int pos;
	protected boolean hit;

	protected MatcherBase(byte[] w) {
		super();
		
		pat = w;
		pos = 0;
		hit = false;
	}

	/** Sets the index to start search at; 
	 * returns the same object for method chaining.	 */
	@Override
	public StreamBufferMatcher setIndex(int index) { pos = index; return this; }

	@Override
	public int getIndex() { return pos; }

	protected void setFound(boolean found) { hit = found; }

	@Override
	public boolean found() { return hit; }

	/**
	 * Resets the found() predicate to false and starts search, reading from
	 * input stream if running out of buffer contents till the end of stream.
	 * If a match has been found, getIndex() returns the index of the first
	 * matching byte in input buffer.
	 * @param input the input buffer and its connected stream
	 * @throws IndexOutOfBoundsException when index out of buffer
	 */
	@Override
	public void find(StreamBuffer input) {
		byte[] buffer = input.getBuffer();
		if(pos < 0 || pos >= buffer.length) 
			throw new IndexOutOfBoundsException("position not in buffer");
		
		setFound(false);
		for(boolean repeat=true; repeat;)
		{
			match(input);
			
			if(found()) break;
			else if (input.read() < 0) break; // end of input stream?
		}
	}

	/** 
	 * Override this to receive a match event, but don't forget to invoke
	 * super.matchEvent(matchStart) for not breaking class integrity. 
	 * @param matchStart
	 */
	@Override
	public void matchEvent(int matchStart) {
		setFound(true);
		pos = matchStart;
	}

}