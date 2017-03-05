package com.kickstarter.ui.toolbars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;

public final class ProjectToolbar extends KSToolbar {
  public ProjectToolbar(final @NonNull Context context) {
    super(context);
  }

  public ProjectToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ProjectToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  public void backIconClick() {
    ((BaseActivity) getContext()).back();
  }
}
