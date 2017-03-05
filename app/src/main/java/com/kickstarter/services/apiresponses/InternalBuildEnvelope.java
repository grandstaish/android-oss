package com.kickstarter.services.apiresponses;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class InternalBuildEnvelope implements Parcelable {
  public abstract @Nullable Integer build();
  public abstract @Nullable String changelog();
  public abstract boolean newerBuildAvailable();

  public abstract static class Builder {
    public abstract Builder build(Integer __);
    public abstract Builder changelog(String __);
    public abstract Builder newerBuildAvailable(boolean __);
    public abstract InternalBuildEnvelope build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
