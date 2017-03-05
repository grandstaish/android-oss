package com.kickstarter.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class User implements Parcelable {
  public abstract Avatar avatar();
  public abstract @Nullable Integer backedProjectsCount();
  public abstract @Nullable Integer createdProjectsCount();
  public abstract @Nullable Boolean gamesNewsletter();
  public abstract @Nullable Boolean happeningNewsletter();
  public abstract long id();
  public abstract @Nullable Location location();
  public abstract String name();
  public abstract @Nullable Boolean notifyMobileOfBackings();
  public abstract @Nullable Boolean notifyMobileOfComments();
  public abstract @Nullable Boolean notifyMobileOfFollower();
  public abstract @Nullable Boolean notifyMobileOfFriendActivity();
  public abstract @Nullable Boolean notifyMobileOfUpdates();
  public abstract @Nullable Boolean notifyOfBackings();
  public abstract @Nullable Boolean notifyOfComments();
  public abstract @Nullable Boolean notifyOfFollower();
  public abstract @Nullable Boolean notifyOfFriendActivity();
  public abstract @Nullable Boolean notifyOfUpdates();
  public abstract @Nullable Boolean promoNewsletter();
  public abstract @Nullable Boolean social();
  public abstract @Nullable Integer starredProjectsCount();
  public abstract @Nullable Boolean weeklyNewsletter();

  public abstract static class Builder {
    public abstract Builder avatar(Avatar __);
    public abstract Builder backedProjectsCount(Integer __);
    public abstract Builder createdProjectsCount(Integer __);
    public abstract Builder gamesNewsletter(Boolean __);
    public abstract Builder happeningNewsletter(Boolean __);
    public abstract Builder id(long __);
    public abstract Builder location(Location __);
    public abstract Builder name(String __);
    public abstract Builder notifyMobileOfBackings(Boolean __);
    public abstract Builder notifyMobileOfComments(Boolean __);
    public abstract Builder notifyMobileOfFollower(Boolean __);
    public abstract Builder notifyMobileOfFriendActivity(Boolean __);
    public abstract Builder notifyMobileOfUpdates(Boolean __);
    public abstract Builder notifyOfBackings(Boolean __);
    public abstract Builder notifyOfComments(Boolean __);
    public abstract Builder notifyOfFollower(Boolean __);
    public abstract Builder notifyOfFriendActivity(Boolean __);
    public abstract Builder notifyOfUpdates(Boolean __);
    public abstract Builder promoNewsletter(Boolean __);
    public abstract Builder social(Boolean __);
    public abstract Builder starredProjectsCount(Integer __);
    public abstract Builder weeklyNewsletter(Boolean __);
    public abstract User build();
  }

  public static Builder builder() {
    return null;
  }

  public @NonNull String param() {
    return String.valueOf(this.id());
  }

  public abstract Builder toBuilder();
}
