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
public interface Matcher {

	/** Only looks for matches in the input buffer starting at getIndex(),
	 * without reading from the buffered stream. 
	 * Sets the found() predicate to false, initially. 
	 * Sets the found() predicate to true, the getIndex() value accordingly 
	 * and invokes matchEvent() on a full match.
	 * @param input the input buffer
	 * @throws IndexOutOfBoundsException when index out of buffer size */
	public abstract void match(StreamBuffer input) throws IndexOutOfBoundsException;

	/** Override this to receive a match event, but don't forget to invoke
	 * super.matchEvent(matchStart) for not breaking class integrity. 
	 * @param matchStart */
	public abstract void matchEvent(int matchStart);

	/** Resets the found() predicate to false and starts search, reading from
	 * input stream if running out of buffer contents till the end of stream.
	 * If a match has been found, getIndex() returns the index of the first
	 * matching byte in input buffer.
	 * @param input the input buffer and its connected stream
	 * @throws IndexOutOfBoundsException when index out of buffer */
	public abstract void find(StreamBuffer input);

	/** Predicate telling if a match was found. 
	 * If true, getIndex() tells the index of the first byte of the match. */
	public abstract boolean found();

	/** The index of the first byte of the match, if found() returns true. */
	public abstract int getIndex();

	/** Sets the index to start search at; 
	 * returns the same object for method chaining. */
	public abstract Matcher setIndex(int index);
	
	/** Resets any internal matcher state so match() would re-start matching
     *	from the beginning of the pattern, starting in buffer at get_index(). */
	public abstract void reset();

}
