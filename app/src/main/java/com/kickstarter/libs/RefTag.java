package com.kickstarter.libs;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.kickstarter.services.DiscoveryParams;

/**
 * A {@link RefTag} is a string identifier that Kickstarter uses to credit a pledge to a source of traffic, e.g. discovery,
 * activity, search, etc. This class represents all possible ref tags we support in the app.
 */
public abstract class RefTag implements Parcelable {
  public abstract @NonNull String tag();

  public static RefTag from(final @NonNull String tag) {
    return null;
  }

  public static @NonNull RefTag category() {
    return null;
  }

  public static @NonNull RefTag category(final @NonNull DiscoveryParams.Sort sort) {
    return null;
  }

  public static @NonNull RefTag city() {
    return null;
  }

  public static @NonNull RefTag recommended() {
    return null;
  }

  public static @NonNull RefTag recommended(final @NonNull DiscoveryParams.Sort sort) {
    return null;
  }

  public static @NonNull RefTag social() {
    return null;
  }

  public static @NonNull RefTag search() {
    return null;
  }

  public static @NonNull RefTag discovery() {
    return null;
  }

  public static @NonNull RefTag thanks() {
    return null;
  }

  public static @NonNull RefTag activity() {
    return null;
  }

  public static @NonNull RefTag discoverPotd() {
    return null;
  }

  public static @NonNull RefTag categoryFeatured() {
    return null;
  }

  public static @NonNull RefTag activitySample() {
    return null;
  }

  public static @NonNull RefTag update() {
    return null;
  }
}
