package com.kickstarter.ui.toolbars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.ui.views.LoginPopupMenu;

public class LoginToolbar extends KSToolbar {
  TextView helpButton;

  public LoginToolbar(final @NonNull Context context) {
    super(context);
  }

  public LoginToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public LoginToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  protected void helpButtonClick() {
    new LoginPopupMenu(getContext(), helpButton).show();
  }
}
