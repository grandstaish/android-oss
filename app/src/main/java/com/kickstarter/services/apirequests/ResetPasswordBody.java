package com.kickstarter.services.apirequests;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class ResetPasswordBody implements Parcelable {
  public abstract String email();

  public abstract static class Builder {
    public abstract Builder email(String __);
    public abstract ResetPasswordBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
