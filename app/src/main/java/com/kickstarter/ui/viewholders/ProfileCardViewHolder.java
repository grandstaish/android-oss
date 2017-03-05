package com.kickstarter.ui.viewholders;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.libs.utils.ProgressBarUtils;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.squareup.picasso.Picasso;

public final class ProfileCardViewHolder extends KSViewHolder {
  private final Delegate delegate;
  private Project project;

  protected TextView fundingUnsuccessfulTextView;
  protected ProgressBar percentageFundedProgressBar;
  protected ImageView profileCardImageView;
  protected TextView profileCardNameTextView;
  protected ViewGroup projectStateViewGroup;
  protected TextView successfullyFundedTextView;

  protected Drawable grayGradientDrawable;

  protected String successfulString;
  protected String unsuccessfulString;
  protected String cancelledString;
  protected String suspendedString;

  public interface Delegate {
    void profileCardViewHolderClicked(ProfileCardViewHolder viewHolder, Project project);
  }

  public ProfileCardViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    project = ObjectUtils.requireNonNull((Project) data, Project.class);
  }

  @Override
  public void onBind() {
    final Photo photo = project.photo();

    if (photo != null) {
      profileCardImageView.setVisibility(View.VISIBLE);
      Picasso.with(context()).load(photo.med())
        .placeholder(grayGradientDrawable)
        .into(profileCardImageView);
    } else {
      profileCardImageView.setVisibility(View.INVISIBLE);
    }

    profileCardNameTextView.setText(project.name());
    percentageFundedProgressBar.setProgress(ProgressBarUtils.progress(project.percentageFunded()));

    setProjectStateView();
  }

  @Override
  public void onClick(final @NonNull View view) {
    delegate.profileCardViewHolderClicked(this, project);
  }

  public void setProjectStateView() {
    switch(project.state()) {
      case Project.STATE_SUCCESSFUL:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        fundingUnsuccessfulTextView.setVisibility(View.GONE);
        successfullyFundedTextView.setVisibility(View.VISIBLE);
        successfullyFundedTextView.setText(successfulString);
        break;
      case Project.STATE_CANCELED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        successfullyFundedTextView.setVisibility(View.GONE);
        fundingUnsuccessfulTextView.setVisibility(View.VISIBLE);
        fundingUnsuccessfulTextView.setText(cancelledString);
        break;
      case Project.STATE_FAILED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        successfullyFundedTextView.setVisibility(View.GONE);
        fundingUnsuccessfulTextView.setVisibility(View.VISIBLE);
        fundingUnsuccessfulTextView.setText(unsuccessfulString);
        break;
      case Project.STATE_SUSPENDED:
        percentageFundedProgressBar.setVisibility(View.GONE);
        projectStateViewGroup.setVisibility(View.VISIBLE);
        successfullyFundedTextView.setVisibility(View.GONE);
        fundingUnsuccessfulTextView.setVisibility(View.VISIBLE);
        fundingUnsuccessfulTextView.setText(suspendedString);
        break;
      default:
        percentageFundedProgressBar.setVisibility(View.VISIBLE);
        projectStateViewGroup.setVisibility(View.GONE);
        break;
    }
  }
}
