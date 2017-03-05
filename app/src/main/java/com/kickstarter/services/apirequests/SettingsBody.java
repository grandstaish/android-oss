package com.kickstarter.services.apirequests;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class SettingsBody {
  public abstract boolean notifyMobileOfFollower();
  public abstract boolean notifyMobileOfFriendActivity();
  public abstract boolean notifyMobileOfUpdates();
  public abstract boolean notifyOfFollower();
  public abstract boolean notifyOfFriendActivity();
  public abstract boolean notifyOfUpdates();
  public abstract int gamesNewsletter();
  public abstract int happeningNewsletter();
  public abstract int promoNewsletter();
  public abstract int weeklyNewsletter();

  public abstract static class Builder {
    public abstract Builder notifyMobileOfFollower(boolean __);
    public abstract Builder notifyMobileOfFriendActivity(boolean __);
    public abstract Builder notifyMobileOfUpdates(boolean __);
    public abstract Builder notifyOfFollower(boolean __);
    public abstract Builder notifyOfFriendActivity(boolean __);
    public abstract Builder notifyOfUpdates(boolean __);
    public abstract Builder gamesNewsletter(int __);
    public abstract Builder happeningNewsletter(int __);
    public abstract Builder promoNewsletter(int __);
    public abstract Builder weeklyNewsletter(int __);
    public abstract SettingsBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
