package com.kickstarter.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.SwitchCompatUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.ui.toolbars.LoginToolbar;
import com.kickstarter.ui.views.LoginPopupMenu;
import com.kickstarter.viewmodels.SignupViewModel;

import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromLeft;

@RequiresActivityViewModel(SignupViewModel.class)
public final class SignupActivity extends BaseActivity<SignupViewModel> {
  EditText nameEditText;
  EditText emailEditText;
  TextView helpButton;
  LoginToolbar loginToolbar;
  EditText passwordEditText;
  Button signupButton;
  SwitchCompat newsletterSwitch;
  TextView disclaimerTextView;

  String signUpString;
  String errorTitleString;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.signup_layout);
    loginToolbar.setTitle(signUpString);

    viewModel.outputs.signupSuccess()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> onSuccess());

    viewModel.outputs.formSubmitting()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::setFormDisabled);

    viewModel.outputs.formIsValid()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::setFormEnabled);

    viewModel.outputs.sendNewslettersIsChecked()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(b -> SwitchCompatUtils.setCheckedWithoutAnimation(newsletterSwitch, b));

    viewModel.errors.signupError()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(e -> ViewUtils.showDialog(this, errorTitleString, e));

    RxView.clicks(newsletterSwitch)
      .skip(1)
      .compose(bindToLifecycle())
      .subscribe(__ -> viewModel.inputs.sendNewslettersClick(newsletterSwitch.isChecked()));
  }

  public void disclaimerClick() {
    new LoginPopupMenu(this, helpButton).show();
  }

  void onNameTextChanged(final @NonNull CharSequence fullName) {
    viewModel.inputs.fullName(fullName.toString());
  }

  void onEmailTextChanged(final @NonNull CharSequence email) {
    viewModel.inputs.email(email.toString());
  }

  void onPasswordTextChange(final @NonNull CharSequence password) {
    viewModel.inputs.password(password.toString());
  }

  public void signupButtonOnClick() {
    viewModel.inputs.signupClick();
  }

  public void onSuccess() {
    setResult(Activity.RESULT_OK);
    finish();
  }

  public void setFormEnabled(final boolean enabled) {
    signupButton.setEnabled(enabled);
  }

  public void setFormDisabled(final boolean disabled) {
    setFormEnabled(!disabled);
  }

  protected @Nullable Pair<Integer, Integer> exitTransition() {
    return slideInFromLeft();
  }
}
