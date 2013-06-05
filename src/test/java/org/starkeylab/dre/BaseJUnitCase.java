package org.starkeylab.dre;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

public class BaseJUnitCase extends TestCase{
	
	protected File getFileResource(String resource) throws IOException {
        URL url = getClass().getResource(resource);
        assertNotNull(url);
        return new File(url.getFile());
    }


}
