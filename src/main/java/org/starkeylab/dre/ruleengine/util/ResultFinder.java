package org.starkeylab.dre.ruleengine.util;

import java.util.Iterator;
import java.util.List;

public class ResultFinder {
	
	public static Object getResult(List results,String className) {
		if(results==null||results.size()==0||className==null||className.length()==0)
			return null;
		
		// Loop over the results.
		Iterator itr = results.iterator();
		while (itr.hasNext()) {
			Object obj = itr.next();
			if (obj.getClass().getName().equals(className))
				return obj;
		}
		
		return null;

	}

}
