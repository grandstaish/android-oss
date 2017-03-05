package com.kickstarter.services.apirequests;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class RegisterWithFacebookBody implements Parcelable {
  public abstract String accessToken();
  public abstract boolean newsletterOptIn();
  public abstract boolean sendNewsletters();

  public abstract static class Builder {
    public abstract Builder accessToken(String __);
    public abstract Builder newsletterOptIn(boolean __);
    public abstract Builder sendNewsletters(boolean __);
    public abstract RegisterWithFacebookBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
