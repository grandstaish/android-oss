package com.kickstarter.models;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

@AutoGson
public abstract class RewardsItem implements Parcelable {
  public abstract long id();
  public abstract Item item();
  public abstract long itemId();
  public abstract int quantity();
  public abstract long rewardId();

  public abstract static class Builder {
    public abstract Builder id(long __);
    public abstract Builder item(Item __);
    public abstract Builder itemId(long __);
    public abstract Builder quantity(int __);
    public abstract Builder rewardId(long __);
    public abstract RewardsItem build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
