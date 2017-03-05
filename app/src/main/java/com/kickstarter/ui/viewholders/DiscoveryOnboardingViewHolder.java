package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public final class DiscoveryOnboardingViewHolder extends KSViewHolder {
  protected Button lgoinToutButton;

  private final Delegate delegate;
  public interface Delegate {
    void discoveryOnboardingViewHolderLoginToutClick(DiscoveryOnboardingViewHolder viewHolder);
  }

  public DiscoveryOnboardingViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {}

  public void onBind() {}

  protected void loginToutClick() {
    delegate.discoveryOnboardingViewHolderLoginToutClick(this);
  }
}
