package com.kickstarter.ui.toolbars;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.ApiCapabilities;
import com.kickstarter.libs.CurrentUserType;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.Logout;
import com.kickstarter.libs.utils.DiscoveryUtils;
import com.kickstarter.libs.utils.StatusBarUtils;
import com.kickstarter.services.DiscoveryParams;
import com.kickstarter.ui.activities.ActivityFeedActivity;
import com.kickstarter.ui.activities.DiscoveryActivity;
import com.kickstarter.ui.activities.SearchActivity;

import javax.inject.Inject;

import rx.Observable;

public final class DiscoveryToolbar extends KSToolbar {
  TextView activityFeedButton;
  TextView filterTextView;
  View discoveryStatusBar;
  TextView menuButton;
  TextView searchButton;
  @Inject CurrentUserType currentUser;
  @Inject KSString ksString;
  @Inject Logout logout;

  public DiscoveryToolbar(final @NonNull Context context) {
    super(context);
  }

  public DiscoveryToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DiscoveryToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    if (isInEditMode()) {
      return;
    }

    ((KSApplication) getContext().getApplicationContext()).component().inject(this);

    activityFeedButton.setOnClickListener(v -> {
      final Context context = getContext();
      context.startActivity(new Intent(context, ActivityFeedActivity.class));
    });
  }

  protected void menuButtonClick() {
    final DiscoveryActivity activity = (DiscoveryActivity) getContext();
    activity.discoveryLayout().openDrawer(GravityCompat.START);
  }

  public void loadParams(final @NonNull DiscoveryParams params) {
    final DiscoveryActivity activity = (DiscoveryActivity) getContext();

    filterTextView.setText(params.filterString(activity, ksString, true, false));

    if (ApiCapabilities.canSetStatusBarColor() && ApiCapabilities.canSetDarkStatusBarIcons()) {
      discoveryStatusBar.setBackgroundColor(DiscoveryUtils.secondaryColor(activity, params.category()));
      if (DiscoveryUtils.overlayShouldBeLight(params.category())) {
        StatusBarUtils.setLightStatusBarIcons(activity);
      } else {
        StatusBarUtils.setDarkStatusBarIcons(activity);
      }
    }

    this.setBackgroundColor(DiscoveryUtils.primaryColor(activity, params.category()));

    final Observable<TextView> views = Observable.just(activityFeedButton,
      filterTextView,
      menuButton,
      searchButton);

    final @ColorInt int overlayTextColor = DiscoveryUtils.overlayTextColor(activity, params.category());

    views.subscribe(view -> view.setTextColor(overlayTextColor));
  }

  public void searchButtonClick(final @NonNull View view) {
    final Context context = getContext();
    context.startActivity(new Intent(context, SearchActivity.class));
  }
}
