package test.test_xtend_maven;

interface ClickListener {
  public void perform(Button b);
}

public class Button {
  public String getName() {
    return "Button";
  }

  public void onClick(ClickListener listener) {
    listener.perform(this);
  }
}
