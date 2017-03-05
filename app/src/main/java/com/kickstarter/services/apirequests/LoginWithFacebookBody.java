package com.kickstarter.services.apirequests;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class LoginWithFacebookBody implements Parcelable {
  public abstract String accessToken();
  public abstract @Nullable String code();

  public abstract static class Builder {
    public abstract Builder accessToken(String __);
    public abstract Builder code(String __);
    public abstract LoginWithFacebookBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
