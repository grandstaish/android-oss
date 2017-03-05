package com.kickstarter.models;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

import org.joda.time.DateTime;

@AutoGson
public abstract class Comment implements Parcelable {
  public abstract User author();
  public abstract String body();
  public abstract DateTime createdAt();
  public abstract DateTime deletedAt();
  public abstract long id();

  public abstract static class Builder {
    public abstract Builder author(User __);
    public abstract Builder body(String __);
    public abstract Builder createdAt(DateTime __);
    public abstract Builder deletedAt(DateTime __);
    public abstract Builder id(long __);
    public abstract Comment build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
