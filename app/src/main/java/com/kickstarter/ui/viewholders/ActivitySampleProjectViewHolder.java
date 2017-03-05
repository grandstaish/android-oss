package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.models.Activity;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.kickstarter.models.Update;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ActivitySampleProjectViewHolder extends KSViewHolder {
  @Inject KSString ksString;

  protected LinearLayout activityClickArea;
  protected ImageView activityImageView;
  protected TextView activityTitleTextView;
  protected TextView activitySubtitleTextView;
  protected Button seeActivityButton;
  protected String categoryFailureString;
  protected String categoryLaunchString;
  protected String categorySuccessString;
  protected String categoryCancellationString;
  protected String categoryUpdateString;

  private Activity activity;

  private final Delegate delegate;
  public interface Delegate {
    void activitySampleProjectViewHolderSeeActivityClicked(ActivitySampleProjectViewHolder viewHolder);
    void activitySampleProjectViewHolderProjectClicked(ActivitySampleProjectViewHolder viewHolder, Project project);
    void activitySampleProjectViewHolderUpdateClicked(ActivitySampleProjectViewHolder viewHolder, Activity activity);
  }

  public ActivitySampleProjectViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
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

    final Project project = activity.project();
    if (project != null) {

      final Photo photo = project.photo();
      if (photo != null) {
        Picasso.with(context)
          .load(photo.little())
          .into(activityImageView);
      }

      activityTitleTextView.setText(project.name());

      switch(activity.category()) {
        case Activity.CATEGORY_FAILURE:
          activitySubtitleTextView.setText(categoryFailureString);
          break;
        case Activity.CATEGORY_CANCELLATION:
          activitySubtitleTextView.setText(categoryCancellationString);
          break;
        case Activity.CATEGORY_LAUNCH:
          final User user = activity.user();
          if (user != null) {
            activitySubtitleTextView.setText(ksString.format(categoryLaunchString, "user_name", user.name()));
          }
          break;
        case Activity.CATEGORY_SUCCESS:
          activitySubtitleTextView.setText(categorySuccessString);
          break;
        case Activity.CATEGORY_UPDATE:
          final Update update = activity.update();
          if (update != null) {
            activitySubtitleTextView.setText(ksString.format(categoryUpdateString,
              "update_number", String.valueOf(update.sequence()),
              "update_title", update.title()));
          }
          break;
        default:
          break;
      }
    }
  }

  protected void seeActivityOnClick() {
    delegate.activitySampleProjectViewHolderSeeActivityClicked(this);
  }

  protected void activityProjectOnClick() {
    if (activity.category().equals(Activity.CATEGORY_UPDATE)) {
      delegate.activitySampleProjectViewHolderUpdateClicked(this, activity);
    } else {
      delegate.activitySampleProjectViewHolderProjectClicked(this, activity.project());
    }
  }
}
