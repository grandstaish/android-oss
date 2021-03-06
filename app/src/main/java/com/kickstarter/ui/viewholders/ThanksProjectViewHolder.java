package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.ProjectUtils;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class ThanksProjectViewHolder extends KSViewHolder {
  private Project project;
  private final Context context;
  private final Delegate delegate;

  protected TextView timeToGoTextView;
  protected TextView nameTextView;
  protected ImageView photoImageView;
  protected String timeLeftToGoString;

  protected @Inject KSString ksString;

  public interface Delegate {
    void projectClick(ThanksProjectViewHolder viewHolder, Project project);
  }

  public ThanksProjectViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    this.context = view.getContext();
    ((KSApplication) context.getApplicationContext()).component().inject(this);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    project = requireNonNull((Project) data, Project.class);
  }

  public void onBind() {
    nameTextView.setText(project.name());

    if (project.isLive()) {
      timeToGoTextView.setText(ksString.format(
        timeLeftToGoString,
        "time_left",
        ProjectUtils.deadlineCountdown(project, context)
      ));
      timeToGoTextView.setVisibility(View.VISIBLE);
    } else {
      timeToGoTextView.setVisibility(View.GONE);
    }

    final Photo photo = project.photo();
    if (photo != null) {
      photoImageView.setVisibility(View.VISIBLE);
      Picasso.with(context).load(photo.med()).into(photoImageView);
    } else {
      photoImageView.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public void onClick(final @NonNull View view) {
    delegate.projectClick(this, project);
  }
}
