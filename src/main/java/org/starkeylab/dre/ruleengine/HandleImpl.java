package org.starkeylab.dre.ruleengine;

import javax.rules.Handle;

public class HandleImpl implements Handle {

  private Object object;


  public HandleImpl(Object o) {
    object = o;
  }


  public final Object getObject() {
    return object;
  }


  public final void setObject(Object o) {
    object = o;
  }


  public final boolean equals(Object o) {
    if(!(o instanceof HandleImpl))
      return false;
    else
      return object.equals(((HandleImpl)o).object);
  }


  public final int hashCode() {
    return object.hashCode();
  }


}