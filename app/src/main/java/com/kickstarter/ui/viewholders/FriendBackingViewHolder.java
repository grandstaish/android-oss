package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.libs.utils.SocialUtils;
import com.kickstarter.models.Activity;
import com.kickstarter.models.Category;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public final class FriendBackingViewHolder extends ActivityListViewHolder {
  protected ImageView avatarImageView;
  protected TextView creatorNameTextView;
  protected TextView projectNameTextView;
  protected ImageView projectPhotoImageView;
  protected TextView titleTextView;

  protected String projectByCreatorString;

  @Inject KSString ksString;

  private final Delegate delegate;

  public interface Delegate {
    void friendBackingClicked(FriendBackingViewHolder viewHolder, Activity activity);
  }

  public FriendBackingViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    ((KSApplication) view.getContext().getApplicationContext()).component().inject(this);
  }

  @Override
  public void onBind() {
    final Context context = context();

    final User activityUser = activity().user();
    if (activityUser == null) {
      return;
    }
    final Project activityProject = activity().project();
    if (activityProject == null) {
      return;
    }
    final User projectCreator = activityProject.creator();
    if (projectCreator == null) {
      return;
    }
    final Category projectCategory = activityProject.category();
    if (projectCategory == null) {
      return;
    }
    final Photo projectPhoto = activityProject.photo();
    if (projectPhoto == null) {
      return;
    }

    Picasso.with(context)
      .load(activityUser.avatar().small())
      .transform(new CircleTransformation())
      .into(avatarImageView);

    creatorNameTextView.setText(ksString.format(
      projectByCreatorString,
      "creator_name",
      projectCreator.name()
    ));

    projectNameTextView.setText(activityProject.name());

    Picasso.with(context)
      .load(projectPhoto.little())
      .into(projectPhotoImageView);

    titleTextView.setText(SocialUtils.friendBackingActivityTitle(context,
      activityUser.name(),
      projectCategory.rootId(),
      ksString
    ));
  }

  public void onClick() {
    delegate.friendBackingClicked(this, activity());
  }
}

