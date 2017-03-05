package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.DateTimeUtils;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.models.Activity;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.kickstarter.models.Update;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import javax.inject.Inject;

public final class ProjectUpdateViewHolder extends ActivityListViewHolder {
  protected TextView projectNameTextView;
  protected ImageView projectPhotoImageView;
  protected TextView timestampTextView;
  protected TextView updateBodyTextView;
  protected TextView updateSequenceTextView;
  protected TextView updateTitleTextView;

  protected String projectUpdateCountString;

  private final Delegate delegate;

  protected @Inject KSString ksString;

  public interface Delegate {
    void projectUpdateProjectClicked(ProjectUpdateViewHolder viewHolder, Activity activity);
    void projectUpdateClicked(ProjectUpdateViewHolder viewHolder, Activity activity);
  }

  public ProjectUpdateViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    ((KSApplication) view.getContext().getApplicationContext()).component().inject(this);
  }

  @Override
  public void onBind() {
    final Context context = context();

    final Project project = activity().project();
    if (project == null) {
      return;
    }
    final User user = activity().user();
    if (user == null) {
      return;
    }
    final Photo photo = project.photo();
    if (photo == null) {
      return;
    }
    final Update update = activity().update();
    if (update == null) {
      return;
    }
    final DateTime publishedAt = ObjectUtils.coalesce(update.publishedAt(), new DateTime());

    projectNameTextView.setText(project.name());

    Picasso.with(context)
      .load(photo.little())
      .into(projectPhotoImageView);

    timestampTextView.setText(DateTimeUtils.relative(context, ksString, publishedAt));

    updateBodyTextView.setText(update.truncatedBody());

    updateSequenceTextView.setText(ksString.format(
      projectUpdateCountString,
      "update_count",
      String.valueOf(update.sequence())
    ));

    updateTitleTextView.setText(update.title());
  }

  public void projectOnClick() {
    delegate.projectUpdateProjectClicked(this, activity());
  }

  public void updateOnClick() {
    delegate.projectUpdateClicked(this, activity());
  }
}
