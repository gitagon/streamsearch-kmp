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

public class KmpExample {

	public static void main(String[] args) 
	{
		final String wString = "abbab"; // pattern
		final String textStr = "aabbaabbabababbbc"; // text stream
		final byte[] w = wString.getBytes(); // pattern
		final byte[] text = textStr.getBytes(); // text stream

		System.out.println("pattern of length "+w.length+": "+wString);
		System.out.println("text: "+textStr);
		
		// reads from text array
		InputStream input = new InputStream() {

			int i = 0;
			public int read() throws IOException 
			{
				int result = i < text.length ? text[i] : -1;
				System.out.println("read @"+i+" "+Character.valueOf((char) result));
				if(result >= 0) i++;
				return result;
			}
			
		};
		StreamBuffer buf = new InputStreamBuffer(input, 1); 
		Matcher m = MatcherKMP.compile(w);
		m.find(buf);
		System.out.println("found match: "+m.found()+" at position "+m.getIndex());
	}

}
