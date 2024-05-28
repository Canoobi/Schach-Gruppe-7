package hwr.oop.chess.persistance;

import java.io.IOException;

public enum IOExceptionBomb {
  DO {
    @Override
    void fire() throws IOException {
      throw new IOException(MESSAGE);
    }
  },
  DONT {
    @Override
    void fire() {
      // do nothing
    }
  };

  public static final String MESSAGE = "Here goes everything exploding...";

  abstract void fire() throws IOException;
}
