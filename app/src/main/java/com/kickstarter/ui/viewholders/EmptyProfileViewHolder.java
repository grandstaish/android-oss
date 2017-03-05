package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.kickstarter.R;

public final class EmptyProfileViewHolder extends KSViewHolder {
  protected Button exploreButton;

  private final Delegate delegate;

  public interface Delegate {
    void emptyProfileViewHolderExploreProjectsClicked(EmptyProfileViewHolder viewHolder);
  }

  public EmptyProfileViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {}

  @Override
  public void onBind() {}

  public void exploreProjectsClicked() {
    delegate.emptyProfileViewHolderExploreProjectsClicked(this);
  }
}
