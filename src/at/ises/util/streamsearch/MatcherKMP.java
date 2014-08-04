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
 * Knuth-Morris-Pratt matcher implementation for InputStream substrates.
 * Helpful for implementation were:
 * - the (German) wikipedia description for the algorithm.
 * - the definition of border, prefix, etc. on W. Lang's page:
 * http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmp.htm
 * 
 * KMP algorithm reference:
 * D. E. Knuth, J. H. Morris, V. R. Pratt: Fast Pattern Matching in Strings. 
 * In: SIAM Journal of Computing. 6, 2, 323-350 (1977)
 * @author @gitagon
 */
public class MatcherKMP extends MatcherBase implements StreamBufferMatcher
{
	final int[] tab;  // prefix table for the pattern
	private MatcherKMP(byte[] w) 
	{
		super(w);
		tab = new int[w.length+1];
	}
	
	
	private void compile()
	{
		final int n = pat.length;
		int i = 0;       // index of current position in pattern
		int j = -1;      // length of prefix under consideration
		tab[i] = j;      // first table entry is always -1
		while(i < n)     // do until the end of pattern has been reached
		{
			while( j >= 0 && pat[j] != pat[i] )  
				j = tab[j]; // in case a proper prefix cannot be extended,
				            // look for a shorter one.
			
			// in this place EITHER of j=-1 OR w[i]=w[j] hold true
			 
			i++;         // for the next byte in pattern
			j++;         // the computed prefix length (minimum: 0)
			tab[i] = j;  // is entered into the prefix table
		 }
	}
	
	
	public static StreamBufferMatcher compile(byte[] patternW)
	{
		MatcherKMP result = new MatcherKMP(patternW);
		result.compile();
		return result;
	}
	
	
	/**
	 * Only looks for matches in the input buffer starting at getIndex(),
	 * without reading from the buffered stream. 
	 * Sets the found() predicate to false, initially. 
	 * Sets the found() predicate to true, the getIndex() value accordingly 
	 * and invokes matchEvent() on a full match.
	 * @param input the input buffer
	 * @throws IndexOutOfBoundsException when index out of buffer size
	 */
	@Override
	public void match(StreamBuffer input) throws IndexOutOfBoundsException 
	{
		setFound(false);
		final int m = pat.length;
		final byte[] txt = input.getBuffer();
		final int n = input.size();
		
//		input values:
//		- pattern pat of length m
//		- lookup array tab of length m+1 which was setup in compile()
//		- text txt of length n to be searched for a match
//		
//		output reported via matchEvent():
//		all matches of pat in txt
		int k = pos; // points at current position in text
		int j = 0;   // points at current position in pattern
		
		loop:
		while (k < n)   // assert not at end of buffer
		{
			while (j >= 0 && txt[k] != pat[j]) // move pattern until
			{                                  // text and pattern match at
				j = tab[j];                    // position k,j using tab array
			}
		 
		    k++;                  // move on in text
		    j++;                  // and in pattern
		 
		    if (j == m)           // if at end of pattern
		    {                     // report a match which started
		       matchEvent(k - m); // m bytes earlier.
		       break loop;
//		       j = tab[j];        // move pattern if using a non-breaking loop
		    
		    }
		}
	}

}
