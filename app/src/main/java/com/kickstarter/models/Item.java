package com.kickstarter.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class Item implements Parcelable {
  public abstract float amount();
  public abstract @Nullable String description();
  public abstract long id();
  public abstract String name();
  public abstract long projectId();
  public abstract @Nullable Boolean taxable();

  public abstract static class Builder {
    public abstract Builder amount(float __);
    public abstract Builder description(String __);
    public abstract Builder id(long __);
    public abstract Builder name(String __);
    public abstract Builder projectId(long __);
    public abstract Builder taxable(Boolean __);
    public abstract Item build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
