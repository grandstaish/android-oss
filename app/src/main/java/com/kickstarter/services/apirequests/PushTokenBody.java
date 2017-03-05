package com.kickstarter.services.apirequests;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class PushTokenBody implements Parcelable {
  public abstract String pushServer();
  public abstract String token();

  public abstract static class Builder {
    public abstract Builder pushServer(String __);
    public abstract Builder token(String __);
    public abstract PushTokenBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}

