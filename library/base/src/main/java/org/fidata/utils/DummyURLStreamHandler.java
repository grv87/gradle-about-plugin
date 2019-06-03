package org.fidata.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class DummyURLStreamHandler extends URLStreamHandler {
  @Override
  protected URLConnection openConnection(URL u) throws IOException {
    throw new UnsupportedOperationException("This is dummy URL stream handler which is not able to open any connections");
  }
}
