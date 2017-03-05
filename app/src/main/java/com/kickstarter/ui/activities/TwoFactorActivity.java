package com.kickstarter.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.ui.toolbars.LoginToolbar;
import com.kickstarter.viewmodels.TwoFactorViewModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromLeft;

@RequiresActivityViewModel(TwoFactorViewModel.class)
public final class TwoFactorActivity extends BaseActivity<TwoFactorViewModel> {
  public EditText codeEditText;
  public Button resendButton;
  public Button loginButton;
  public LoginToolbar loginToolbar;

  String codeMismatchString;
  String unableToLoginString;
  String verifyString;
  String errorTitleString;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.two_factor_layout);
    loginToolbar.setTitle(verifyString);

    viewModel.outputs.tfaSuccess()
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

    errorMessages()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(e -> ViewUtils.showDialog(this, errorTitleString, e));
  }

  private Observable<String> errorMessages() {
    return viewModel.errors.tfaCodeMismatchError().map(__ -> codeMismatchString)
      .mergeWith(viewModel.errors.genericTfaError().map(__ -> unableToLoginString));
  }

  public void codeEditTextOnTextChanged(final @NonNull CharSequence code) {
    viewModel.inputs.code(code.toString());
  }

  public void resendButtonOnClick() {
    viewModel.inputs.resendClick();
  }

  public void loginButtonOnClick() {
    viewModel.inputs.loginClick();
  }

  public void onSuccess() {
    setResult(Activity.RESULT_OK);
    finish();
  }

  public void setFormEnabled(final boolean enabled) {
    loginButton.setEnabled(enabled);
  }

  public void setFormDisabled(final boolean disabled) {
    setFormEnabled(!disabled);
  }

  protected @Nullable Pair<Integer, Integer> exitTransition() {
    return slideInFromLeft();
  }
}
