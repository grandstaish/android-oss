package com.kickstarter.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.kickstarter.R;
import com.kickstarter.libs.ActivityRequestCodes;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.services.apiresponses.ErrorEnvelope;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.ui.toolbars.LoginToolbar;
import com.kickstarter.ui.views.LoginPopupMenu;
import com.kickstarter.viewmodels.LoginToutViewModel;

import java.util.Arrays;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromRight;
import static com.kickstarter.libs.utils.TransitionUtils.transition;

@RequiresActivityViewModel(LoginToutViewModel.class)
public final class LoginToutActivity extends BaseActivity<LoginToutViewModel> {
  TextView disclaimerTextView;
  Button loginButton;
  Button facebookButton;
  Button signupButton;
  TextView helpButton;
  LoginToolbar loginToolbar;

  String loginOrSignUpString;
  String loginErrorTitleString;
  String unableToLoginString;
  String errorTitleString;
  String troubleLoggingInString;
  String tryAgainString;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.login_tout_layout);
    loginToolbar.setTitle(loginOrSignUpString);

    viewModel.outputs.finishWithSuccessfulResult()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> finishWithSuccessfulResult());

    viewModel.outputs.startLoginActivity()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> startLogin());

    viewModel.outputs.startSignupActivity()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> startSignup());

    viewModel.outputs.startFacebookConfirmationActivity()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(ua -> startFacebookConfirmationActivity(ua.first, ua.second));

    viewModel.outputs.showFacebookAuthorizationErrorDialog()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> ViewUtils.showDialog(this, errorTitleString, troubleLoggingInString, tryAgainString));

    showErrorMessageToasts()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(ViewUtils.showToast(this));

    viewModel.outputs.startTwoFactorChallenge()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> startTwoFactorFacebookChallenge());

    viewModel.outputs.showUnauthorizedErrorDialog()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(errorMessage -> ViewUtils.showDialog(this, loginErrorTitleString, errorMessage));
  }

  private @NonNull Observable<String> showErrorMessageToasts() {
    return viewModel.outputs.showMissingFacebookEmailErrorToast()
      .map(ObjectUtils.coalesceWith(unableToLoginString))
      .mergeWith(
        viewModel.outputs.showFacebookInvalidAccessTokenErrorToast()
          .map(ObjectUtils.coalesceWith(unableToLoginString))
      );
  }

  public void disclaimerTextViewClick() {
    new LoginPopupMenu(this, helpButton).show();
  }

  public void facebookLoginClick() {
    viewModel.inputs.facebookLoginClick(this,
      Arrays.asList(getResources().getStringArray(R.array.facebook_permissions_array))
    );
  }

  public void loginButtonClick() {
    viewModel.inputs.loginClick();
  }

  public void signupButtonClick() {
    viewModel.inputs.signupClick();
  }

  private void finishWithSuccessfulResult() {
    setResult(Activity.RESULT_OK);
    finish();
  }

  public void startFacebookConfirmationActivity(final @NonNull ErrorEnvelope.FacebookUser facebookUser,
    final @NonNull String accessTokenString) {
    final Intent intent = new Intent(this, FacebookConfirmationActivity.class)
      .putExtra(IntentKey.FACEBOOK_USER, facebookUser)
      .putExtra(IntentKey.FACEBOOK_TOKEN, accessTokenString);
    startActivityForResult(intent, ActivityRequestCodes.LOGIN_FLOW);
    transition(this, slideInFromRight());
  }

  private void startLogin() {
    final Intent intent = new Intent(this, LoginActivity.class);
    startActivityForResult(intent, ActivityRequestCodes.LOGIN_FLOW);
    transition(this, slideInFromRight());
  }

  private void startSignup() {
    final Intent intent = new Intent(this, SignupActivity.class);
    startActivityForResult(intent, ActivityRequestCodes.LOGIN_FLOW);
    transition(this, slideInFromRight());
  }

  public void startTwoFactorFacebookChallenge() {
    final Intent intent = new Intent(this, TwoFactorActivity.class)
      .putExtra(IntentKey.FACEBOOK_LOGIN, true)
      .putExtra(IntentKey.FACEBOOK_TOKEN, AccessToken.getCurrentAccessToken().getToken());

    startActivityForResult(intent, ActivityRequestCodes.LOGIN_FLOW);
    transition(this, slideInFromRight());
  }
}
