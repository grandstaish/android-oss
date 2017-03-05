package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

public final class FriendFollowViewHolder extends ActivityListViewHolder {
  protected ImageView avatarImageView;
  protected TextView titleTextView;
  
  protected String isFollowingYouString;
  protected String notImplementedYetString;

  public FriendFollowViewHolder(final @NonNull View view) {
    super(view);
  }

  @Override
  public void onBind() {
    final Context context = context();

    final User friend = activity().user();
    if (friend == null) {
      return;
    }

    Picasso.with(context)
      .load(friend.avatar().small())
      .transform(new CircleTransformation())
      .into(avatarImageView);

    // TODO: bold username
    titleTextView.setText(
      new StringBuilder(friend.name())
        .append(" ")
        .append(isFollowingYouString)
    );
  }
}
