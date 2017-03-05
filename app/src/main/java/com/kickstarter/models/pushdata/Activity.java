package com.kickstarter.models.pushdata;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class Activity implements Parcelable {
  @com.kickstarter.models.Activity.Category public abstract String category();
  public abstract @Nullable Long commentId();
  public abstract long id();
  public abstract @Nullable Long projectId();
  public abstract @Nullable String projectPhoto();
  public abstract @Nullable String userPhoto();
  public abstract @Nullable Long updateId();

  public abstract static class Builder {
    public abstract Builder commentId(Long __);
    public abstract Builder category(@com.kickstarter.models.Activity.Category String __);
    public abstract Builder id(long __);
    public abstract Builder projectId(Long __);
    public abstract Builder projectPhoto(String __);
    public abstract Builder userPhoto(String __);
    public abstract Builder updateId(Long __);
    public abstract Activity build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();

}
