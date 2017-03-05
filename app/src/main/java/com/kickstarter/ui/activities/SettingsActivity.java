package com.kickstarter.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.Build;
import com.kickstarter.libs.CurrentUserType;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.Logout;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.ApplicationUtils;
import com.kickstarter.libs.utils.SwitchCompatUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.User;
import com.kickstarter.ui.data.Newsletter;
import com.kickstarter.ui.views.IconTextView;
import com.kickstarter.viewmodels.SettingsViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.BooleanUtils.isTrue;
import static com.kickstarter.libs.utils.IntegerUtils.intValueOrZero;

@RequiresActivityViewModel(SettingsViewModel.class)
public final class SettingsActivity extends BaseActivity<SettingsViewModel> {
  protected SwitchCompat gamesNewsletterSwitch;
  protected SwitchCompat happeningNewsletterSwitch;
  protected IconTextView friendActivityMailIconTextView;
  protected IconTextView friendActivityPhoneIconTextView;
  protected IconTextView newFollowersMailIconTextView;
  protected IconTextView newFollowersPhoneIconTextView;
  protected TextView projectNotificationsCountTextView;
  protected IconTextView projectUpdatesMailIconTextView;
  protected IconTextView projectUpdatesPhoneIconTextView;
  protected SwitchCompat promoNewsletterSwitch;
  protected SwitchCompat weeklyNewsletterSwitch;

  protected int green;
  protected int gray;

  protected String gamesNewsletterString;
  protected String happeningNewsletterString;
  protected String mailtoString;
  protected String loggedOutString;
  protected String weeklyNewsletterString;
  protected String promoNewsletterString;
  protected String optInMessageString;
  protected String optInTitleString;
  protected String subscribeMobileString;
  protected String subscribeString;
  protected String supportEmailBodyString;
  protected String supportEmailSubjectString;
  protected String supportEmailString;
  protected String unableToSaveString;
  protected String unsubscribeMobileString;
  protected String unsubscribeString;

  @Inject CurrentUserType currentUser;
  @Inject KSString ksString;
  @Inject Logout logout;
  @Inject Build build;

  private boolean notifyMobileOfFollower;
  private boolean notifyMobileOfFriendActivity;
  private boolean notifyMobileOfUpdates;
  private boolean notifyOfFollower;
  private boolean notifyOfFriendActivity;
  private boolean notifyOfUpdates;
  private AlertDialog logoutConfirmationDialog;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_layout);
    ((KSApplication) getApplication()).component().inject(this);

    viewModel.outputs.user()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::displayPreferences);

    viewModel.outputs.showOptInPrompt()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::showOptInPrompt);

    viewModel.errors.unableToSavePreferenceError()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> ViewUtils.showToast(this, unableToSaveString));

    RxView.clicks(gamesNewsletterSwitch)
      .compose(bindToLifecycle())
      .subscribe(__ -> viewModel.inputs.sendGamesNewsletter(gamesNewsletterSwitch.isChecked()));

    RxView.clicks(happeningNewsletterSwitch)
      .compose(bindToLifecycle())
      .subscribe(__ -> viewModel.inputs.sendHappeningNewsletter(happeningNewsletterSwitch.isChecked()));

    RxView.clicks(promoNewsletterSwitch)
      .compose(bindToLifecycle())
      .subscribe(__ -> viewModel.inputs.sendPromoNewsletter(promoNewsletterSwitch.isChecked()));

    RxView.clicks(weeklyNewsletterSwitch)
      .compose(bindToLifecycle())
      .subscribe(__ -> viewModel.inputs.sendWeeklyNewsletter(weeklyNewsletterSwitch.isChecked()));

    viewModel.outputs.showConfirmLogoutPrompt()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(show -> {
        if (show) {
          lazyLogoutConfirmationDialog().show();
        } else {
          lazyLogoutConfirmationDialog().dismiss();
        }
      });

    viewModel.outputs.logout()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> logout());
  }

  public void contactClick() {
    viewModel.inputs.contactEmailClicked();

    currentUser.observable()
      .take(1)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::composeContactEmail);
  }

  public void cookiePolicyClick() {
    startHelpActivity(HelpActivity.CookiePolicy.class);
  }

  public void faqClick() {
    startHelpActivity(HelpActivity.Faq.class);
  }

  public void howKickstarterWorksClick() {
    startHelpActivity(HelpActivity.HowItWorks.class);
  }

  public void logoutClick() {
    viewModel.inputs.logoutClicked();
  }

  public void manageProjectNotifications() {
    final Intent intent = new Intent(this, ProjectNotificationSettingsActivity.class);
    startActivityWithTransition(intent, R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
  }

  public void privacyPolicyClick() {
    startHelpActivity(HelpActivity.Privacy.class);
  }

  public void startHelpActivity(final @NonNull Class<? extends HelpActivity> helpClass) {
    final Intent intent = new Intent(this, helpClass);
    startActivityWithTransition(intent, R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
  }

  public void toggleNotifyOfFriendActivity() {
    viewModel.inputs.notifyOfFriendActivity(!notifyOfFriendActivity);
  }

  public void toggleNotifyMobileOfFriendActivity() {
    viewModel.inputs.notifyMobileOfFriendActivity(!notifyMobileOfFriendActivity);
  }

  public void toggleNotifyOfNewFollowers() {
    viewModel.inputs.notifyOfFollower(!notifyOfFollower);
  }

  public void toggleNotifyMobileOfNewFollowers() {
    viewModel.inputs.notifyMobileOfFollower(!notifyMobileOfFollower);
  }

  public void toggleNotifyOfUpdates() {
    viewModel.inputs.notifyOfUpdates(!notifyOfUpdates);
  }

  public void toggleNotifyMobileOfUpdates() {
    viewModel.inputs.notifyMobileOfUpdates(!notifyMobileOfUpdates);
  }

  public void termsOfUseClick() {
    startHelpActivity(HelpActivity.Terms.class);
  }

  public void rateUsClick() {
    ViewUtils.openStoreRating(this, getPackageName());
  }

  private void composeContactEmail(final @Nullable User user) {
    final List<String> debugInfo = Arrays.asList(
      user != null ? String.valueOf(user.id()) : loggedOutString,
      build.versionName(),
      android.os.Build.VERSION.RELEASE + " (SDK " + Integer.toString(android.os.Build.VERSION.SDK_INT) + ")",
      android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
    );

    final String body = new StringBuilder()
      .append(supportEmailBodyString)
      .append(TextUtils.join(" | ", debugInfo))
      .toString();

    final Intent intent = new Intent(Intent.ACTION_SENDTO)
      .setData(Uri.parse(mailtoString))
      .putExtra(Intent.EXTRA_SUBJECT, "[Android] " + supportEmailSubjectString)
      .putExtra(Intent.EXTRA_TEXT, body)
      .putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmailString});
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(Intent.createChooser(intent, getString(R.string.support_email_chooser)));
    }
  }

  private void displayPreferences(final @NonNull User user) {
    projectNotificationsCountTextView.setText(String.valueOf(intValueOrZero(user.backedProjectsCount())));

    notifyMobileOfFriendActivity = isTrue(user.notifyMobileOfFriendActivity());
    notifyOfFriendActivity = isTrue(user.notifyOfFriendActivity());
    notifyMobileOfFollower = isTrue(user.notifyMobileOfFollower());
    notifyOfFollower = isTrue(user.notifyOfFollower());
    notifyMobileOfUpdates = isTrue(user.notifyMobileOfUpdates());
    notifyOfUpdates = isTrue(user.notifyOfUpdates());

    toggleIconColor(friendActivityMailIconTextView, false, notifyOfFriendActivity);
    toggleIconColor(friendActivityPhoneIconTextView, true, notifyMobileOfFriendActivity);
    toggleIconColor(newFollowersMailIconTextView, false, notifyOfFollower);
    toggleIconColor(newFollowersPhoneIconTextView, true, notifyMobileOfFollower);
    toggleIconColor(projectUpdatesMailIconTextView, false, notifyOfUpdates);
    toggleIconColor(projectUpdatesPhoneIconTextView, true, notifyMobileOfUpdates);

    SwitchCompatUtils.setCheckedWithoutAnimation(gamesNewsletterSwitch, isTrue(user.gamesNewsletter()));
    SwitchCompatUtils.setCheckedWithoutAnimation(happeningNewsletterSwitch, isTrue(user.happeningNewsletter()));
    SwitchCompatUtils.setCheckedWithoutAnimation(promoNewsletterSwitch, isTrue(user.promoNewsletter()));
    SwitchCompatUtils.setCheckedWithoutAnimation(weeklyNewsletterSwitch, isTrue(user.weeklyNewsletter()));
  }

  /**
   * Lazily creates a logout confirmation dialog and stores it in an instance variable.
   */
  private @NonNull AlertDialog lazyLogoutConfirmationDialog() {
    if (logoutConfirmationDialog == null) {
      logoutConfirmationDialog = new AlertDialog.Builder(this)
        .setTitle(getString(R.string.profile_settings_logout_alert_title))
        .setMessage(getString(R.string.profile_settings_logout_alert_message))
        .setPositiveButton(getString(R.string.profile_settings_logout_alert_confirm_button), (__, ___) -> {
          viewModel.inputs.confirmLogoutClicked();
        })
        .setNegativeButton(getString(R.string.profile_settings_logout_alert_cancel_button), (__, ___) -> {
          viewModel.inputs.closeLogoutConfirmationClicked();
        })
        .setOnCancelListener(__ -> viewModel.inputs.closeLogoutConfirmationClicked())
        .create();
    }
    return logoutConfirmationDialog;
  }

  private void logout() {
    logout.execute();
    ApplicationUtils.startNewDiscoveryActivity(this);
  }

  private @Nullable String newsletterString(final @NonNull Newsletter newsletter) {
    switch (newsletter) {
      case GAMES:
        return gamesNewsletterString;
      case HAPPENING:
        return happeningNewsletterString;
      case PROMO:
        return promoNewsletterString;
      case WEEKLY:
        return weeklyNewsletterString;
      default:
        return null;
    }
  }

  private void showOptInPrompt(final @NonNull Newsletter newsletter) {
    final String string = newsletterString(newsletter);
    if (string == null) {
      return;
    }

    final String optInDialogMessageString = ksString.format(optInMessageString, "newsletter", string);
    ViewUtils.showDialog(this, optInTitleString, optInDialogMessageString);
  }

  private void toggleIconColor(final @NonNull TextView iconTextView, final boolean typeMobile, final boolean enabled) {
    final int color = enabled ? green : gray;
    iconTextView.setTextColor(color);

    String contentDescription = "";
    if (typeMobile && enabled) {
      contentDescription = unsubscribeMobileString;
    }
    if (typeMobile && !enabled) {
      contentDescription = subscribeMobileString;
    }
    if (!typeMobile && enabled) {
      contentDescription = unsubscribeString;
    }
    if (!typeMobile && !enabled) {
      contentDescription = subscribeString;
    }
    iconTextView.setContentDescription(contentDescription);
  }
}
