package com.kickstarter.ui.viewholders.discoverydrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.models.User;
import com.kickstarter.ui.viewholders.KSViewHolder;
import com.squareup.picasso.Picasso;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class LoggedInViewHolder extends KSViewHolder {
  private Delegate delegate;
  private User user;

  protected ImageView userImageView;
  protected TextView userNameTextView;

  public interface Delegate {
    void loggedInViewHolderInternalToolsClick(final @NonNull LoggedInViewHolder viewHolder);
    void loggedInViewHolderProfileClick(final @NonNull LoggedInViewHolder viewHolder, final @NonNull User user);
    void loggedInViewHolderSettingsClick(final @NonNull LoggedInViewHolder viewHolder, final @NonNull User user);
  }

  public LoggedInViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    user = requireNonNull((User) data, User.class);
  }

  @Override
  public void onBind() {
    final Context context = context();

    userNameTextView.setText(user.name());
    Picasso.with(context)
      .load(user.avatar().medium())
      .transform(new CircleTransformation())
      .into(userImageView);
  }

  public void userClick() {
    delegate.loggedInViewHolderProfileClick(this, user);
  }

  @Nullable
  public void internalToolsClick() {
    delegate.loggedInViewHolderInternalToolsClick(this);
  }

  public void settingsClick() {
    delegate.loggedInViewHolderSettingsClick(this, user);
  }
}
