package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.models.Activity;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ActivitySampleFriendFollowViewHolder extends KSViewHolder {
  @Inject KSString ksString;

  protected ImageView activityImageView;
  protected TextView activityTitleTextView;
  protected TextView activitySubtitleTextView;
  protected Button seeActivityButton;
  protected String categoryFollowingString;
  protected String categoryFollowBackString;

  private Activity activity;

  private final Delegate delegate;
  public interface Delegate {
    void activitySampleFriendFollowViewHolderSeeActivityClicked(ActivitySampleFriendFollowViewHolder viewHolder);
  }

  public ActivitySampleFriendFollowViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;

    ((KSApplication) view.getContext().getApplicationContext()).component().inject(this);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    activity = ObjectUtils.requireNonNull((Activity) data, Activity.class);
  }

  public void onBind() {
    final Context context = context();

    final User user = activity.user();
    if (user != null) {
      Picasso.with(context).load(user.avatar()
        .small())
        .transform(new CircleTransformation())
        .into(activityImageView);

      activityTitleTextView.setText(ksString.format(categoryFollowingString, "user_name", user.name()));
      activitySubtitleTextView.setText(categoryFollowBackString);

      // temp until followable :
      activitySubtitleTextView.setVisibility(View.GONE);
    }
  }

  protected void seeActivityOnClick() {
    delegate.activitySampleFriendFollowViewHolderSeeActivityClicked(this);
  }
}
