package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.kickstarter.R;
import com.kickstarter.libs.utils.BooleanUtils;

public final class EmptyActivityFeedViewHolder extends KSViewHolder {
  private boolean isLoggedIn;
  protected Button discoverProjectsButton;
  protected Button loginButton;

  private final Delegate delegate;

  public interface Delegate {
    void emptyActivityFeedDiscoverProjectsClicked(EmptyActivityFeedViewHolder viewHolder);
    void emptyActivityFeedLoginClicked(EmptyActivityFeedViewHolder viewHolder);
  }

  public EmptyActivityFeedViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    this.isLoggedIn = BooleanUtils.isTrue((Boolean) data);
  }

  @Override
  public void onBind() {
    if (isLoggedIn) {
      discoverProjectsButton.setVisibility(View.VISIBLE);
      loginButton.setVisibility(View.GONE);
    } else  {
      discoverProjectsButton.setVisibility(View.GONE);
      loginButton.setVisibility(View.VISIBLE);
    }
  }

  public void discoverProjectsOnClick() {
    delegate.emptyActivityFeedDiscoverProjectsClicked(this);
  }

  public void loginOnClick() {
    delegate.emptyActivityFeedLoginClicked(this);
  }
}
