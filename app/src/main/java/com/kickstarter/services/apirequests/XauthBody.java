package com.kickstarter.services.apirequests;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class XauthBody implements Parcelable {
  public abstract String email();
  public abstract String password();
  public abstract @Nullable String code();

  public abstract static class Builder {
    public abstract Builder email(String __);
    public abstract Builder password(String __);
    public abstract Builder code(String __);
    public abstract XauthBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
