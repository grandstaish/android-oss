package com.kickstarter.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class Location implements Parcelable {
  public abstract long id();
  public abstract String displayableName();
  public abstract String name();
  public abstract @Nullable String city();
  public abstract @Nullable String state();
  public abstract String country();
  public abstract @Nullable Integer projectsCount();

  public abstract static class Builder {
    public abstract Builder displayableName(String __);
    public abstract Builder id(long __);
    public abstract Builder name(String __);
    public abstract Builder city(String __);
    public abstract Builder state(String __);
    public abstract Builder country(String __);
    public abstract Builder projectsCount(Integer __);
    public abstract Location build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
