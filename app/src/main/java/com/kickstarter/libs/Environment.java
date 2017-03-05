package com.kickstarter.libs;

import android.content.SharedPreferences;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.kickstarter.libs.preferences.BooleanPreferenceType;
import com.kickstarter.libs.preferences.IntPreferenceType;
import com.kickstarter.libs.utils.PlayServicesCapability;
import com.kickstarter.services.ApiClientType;
import com.kickstarter.services.WebClientType;

import java.net.CookieManager;

import rx.Scheduler;

public abstract class Environment implements Parcelable {
  public abstract IntPreferenceType activitySamplePreference();
  public abstract AndroidPayCapability androidPayCapability();
  public abstract ApiClientType apiClient();
  public abstract BuildCheck buildCheck();
  public abstract CookieManager cookieManager();
  public abstract CurrentConfigType currentConfig();
  public abstract CurrentUserType currentUser();
  public abstract Gson gson();
  public abstract BooleanPreferenceType hasSeenAppRatingPreference();
  public abstract BooleanPreferenceType hasSeenGamesNewsletterPreference();
  public abstract Koala koala();
  public abstract KSCurrency ksCurrency();
  public abstract KSString ksString();
  public abstract PlayServicesCapability playServicesCapability();
  public abstract Scheduler scheduler();
  public abstract SharedPreferences sharedPreferences();
  public abstract WebClientType webClient();

  public abstract static class Builder {
    public abstract Builder activitySamplePreference(IntPreferenceType __);
    public abstract Builder androidPayCapability(AndroidPayCapability __);
    public abstract Builder apiClient(ApiClientType __);
    public abstract Builder buildCheck(BuildCheck __);
    public abstract Builder cookieManager(CookieManager __);
    public abstract Builder currentConfig(CurrentConfigType __);
    public abstract Builder currentUser(CurrentUserType __);
    public abstract Builder gson(Gson __);
    public abstract Builder hasSeenAppRatingPreference(BooleanPreferenceType __);
    public abstract Builder hasSeenGamesNewsletterPreference(BooleanPreferenceType __);
    public abstract Builder koala(Koala __);
    public abstract Builder ksCurrency(KSCurrency __);
    public abstract Builder ksString(KSString __);
    public abstract Builder playServicesCapability(PlayServicesCapability __);
    public abstract Builder scheduler(Scheduler __);
    public abstract Builder sharedPreferences(SharedPreferences __);
    public abstract Builder webClient(WebClientType __);
    public abstract Environment build();
  }

  public static Builder builder() {
    return null;
  }

  public abstract Builder toBuilder();
}
