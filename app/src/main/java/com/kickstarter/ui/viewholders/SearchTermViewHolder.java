package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.services.DiscoveryParams;

public final class SearchTermViewHolder extends KSViewHolder {
  private DiscoveryParams params;

  protected TextView termTextView;
  protected LinearLayout layout;

  protected String mostPopularString;

  public SearchTermViewHolder(final @NonNull View view) {
    super(view);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
  }

  public void onBind() {
    termTextView.setText(mostPopularString);
  }
}
