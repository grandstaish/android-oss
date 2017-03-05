package com.kickstarter.services.apirequests;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class CommentBody implements Parcelable {
  public abstract String body();

  public abstract static class Builder {
    public abstract Builder body(String __);
    public abstract CommentBody build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
