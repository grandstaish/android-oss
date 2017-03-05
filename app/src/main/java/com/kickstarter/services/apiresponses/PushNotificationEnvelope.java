package com.kickstarter.services.apiresponses;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.models.pushdata.Activity;
import com.kickstarter.models.pushdata.GCM;

import java.util.Arrays;
import java.util.List;

@AutoGson
public abstract class PushNotificationEnvelope implements Parcelable {
  public abstract @Nullable Activity activity();
  public abstract GCM gcm();
  public abstract @Nullable Project project();

  private final static List<String> PROJECT_NOTIFICATION_CATEGORIES = Arrays.asList(
    com.kickstarter.models.Activity.CATEGORY_BACKING,
    com.kickstarter.models.Activity.CATEGORY_CANCELLATION,
    com.kickstarter.models.Activity.CATEGORY_FAILURE,
    com.kickstarter.models.Activity.CATEGORY_LAUNCH,
    com.kickstarter.models.Activity.CATEGORY_SUCCESS);

  public abstract static class Builder {
    public abstract Builder activity(Activity __);
    public abstract Builder gcm(GCM __);
    public abstract Builder project(Project __);
    public abstract PushNotificationEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

  public boolean isFriendFollow() {
    return activity() != null && activity().category().equals(com.kickstarter.models.Activity.CATEGORY_FOLLOW);
  }

  public boolean isProjectActivity() {
    if (activity() != null) {
      return PROJECT_NOTIFICATION_CATEGORIES.contains(activity().category());
    }
    return false;
  }

  public boolean isProjectReminder() {
    return project() != null;
  }

  public boolean isProjectUpdateActivity() {
    return activity() != null && activity().category().equals(com.kickstarter.models.Activity.CATEGORY_UPDATE);
  }

  public int signature() {
    // When we display an Android notification, we can give it a id. If the server sends a notification with the same
    // id, Android updates the existing notification with new information rather than creating a new notification.
    //
    // The server doesn't send unique notification ids, so hashing the alert text is a weak substitute. Probably won't
    // make use of this feature anyhow.
    return gcm().alert().hashCode();
  }

  @AutoGson
  public abstract static class Project implements Parcelable {
    public abstract Long id();
    public abstract String photo();

    public abstract static class Builder {
      public abstract Builder id(Long __);
      public abstract Builder photo(String __);
      public abstract Project build();
    }

    public static Builder builder() {
      return null;
    }
  }

  @AutoGson
  public abstract static class ActivityPushData implements Parcelable {
    public abstract Long id();
    public abstract String category();
    public abstract @Nullable Long projectId();
    public abstract @Nullable String projectPhoto();
    public abstract @Nullable String userPhoto();
    public abstract @Nullable Long commentId();
    public abstract @Nullable Long updateId();
  }
}
