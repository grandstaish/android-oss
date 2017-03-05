package com.kickstarter.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.RefTag;
import com.kickstarter.libs.TweetComposer;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.ApplicationUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.Category;
import com.kickstarter.models.Photo;
import com.kickstarter.models.Project;
import com.kickstarter.services.DiscoveryParams;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.ui.adapters.ThanksAdapter;
import com.kickstarter.viewmodels.ThanksViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kickstarter.libs.rx.transformers.Transformers.ignoreValues;

@RequiresActivityViewModel(ThanksViewModel.class)
public final class ThanksActivity extends BaseActivity<ThanksViewModel> {
  protected @Inject KSString ksString;

  protected TextView backedProjectTextView;
  protected RecyclerView recommendedProjectsRecyclerView;
  protected Button shareButton;
  protected Button shareOnFacebookButton;
  protected Button shareOnTwitterButton;
  protected ImageView woohooBackgroundImageView;

  protected String iJustBackedString;
  protected String shareThisProjectString;
  protected String gamesAlertMessage;
  protected String gamesAlertNo;
  protected String gamesAlertYes;
  protected String okString;
  protected String newsletterGamesString;
  protected String optInMessageString;
  protected String optInTitleString;
  protected String youJustBackedString;

  private ThanksAdapter adapter;
  private ShareDialog shareDialog;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.thanks_layout);
    ((KSApplication) getApplication()).component().inject(this);

    shareDialog = new ShareDialog(this);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    recommendedProjectsRecyclerView.setLayoutManager(layoutManager);

    adapter = new ThanksAdapter(viewModel);
    recommendedProjectsRecyclerView.setAdapter(adapter);

    Observable.timer(500L, TimeUnit.MILLISECONDS, Schedulers.newThread())
      .compose(ignoreValues())
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> animateBackground());

    RxView.clicks(shareButton)
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> viewModel.inputs.shareClick());

    RxView.clicks(shareOnFacebookButton)
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> viewModel.inputs.shareOnFacebookClick());

    RxView.clicks(shareOnTwitterButton)
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> viewModel.inputs.shareOnTwitterClick());

    viewModel.outputs.projectName()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::showBackedProject);

    viewModel.outputs.showConfirmGamesNewsletterDialog()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> showConfirmGamesNewsletterDialog());

    viewModel.outputs.showGamesNewsletterDialog()
      .compose(bindToLifecycle())
      .take(1)
      .delay(700L, TimeUnit.MILLISECONDS)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> showGamesNewsletterDialog());

    viewModel.outputs.showRatingDialog()
      .compose(bindToLifecycle())
      .take(1)
      .delay(700L, TimeUnit.MILLISECONDS)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> showRatingDialog());

    viewModel.outputs.showRecommendations()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::showRecommendations);

    viewModel.outputs.startDiscovery()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startDiscovery);

    viewModel.outputs.startProject()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startProject);

    viewModel.outputs.startShare()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startShare);

    viewModel.outputs.startShareOnFacebook()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startShareOnFacebook);

    viewModel.outputs.startShareOnTwitter()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startShareOnTwitter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    recommendedProjectsRecyclerView.setAdapter(null);
  }

  protected void closeButtonClick() {
    ApplicationUtils.resumeDiscoveryActivity(this);
  }

  private void animateBackground() {
    woohooBackgroundImageView.animate().setDuration(Long.parseLong(getString(R.string.woohoo_duration))).alpha(1);
    final Drawable drawable = woohooBackgroundImageView.getDrawable();
    if (drawable instanceof Animatable) {
      ((Animatable) drawable).start();
    }
  }

  private String shareString(final @NonNull Project project) {
    return ksString.format(iJustBackedString, "project_name", project.name());
  }

  private void showBackedProject(final @NonNull String projectName) {
    backedProjectTextView.setText(Html.fromHtml(ksString.format(youJustBackedString, "project_name", projectName)));
  }

  private void showConfirmGamesNewsletterDialog() {
    final String optInDialogMessageString = ksString.format(optInMessageString, "newsletter", newsletterGamesString);

    final AlertDialog.Builder builder = new AlertDialog.Builder(this)
      .setMessage(optInDialogMessageString)
      .setTitle(optInTitleString)
      .setPositiveButton(okString, (__, ___) -> {});

    builder.show();
  }

  private void showGamesNewsletterDialog() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this)
      .setMessage(gamesAlertMessage)
      .setPositiveButton(gamesAlertYes, (__, ___) -> {
        viewModel.inputs.signupToGamesNewsletterClick();
      })
      .setNegativeButton(gamesAlertNo, (__, ___) -> {
        // Nothing to do!
      });

    builder.show();
  }

  private void showRatingDialog() {
    ViewUtils.showRatingDialog(this);
  }

  private void showRecommendations(final @NonNull Pair<List<Project>, Category> projectsAndRootCategory) {
    adapter.data(projectsAndRootCategory.first, projectsAndRootCategory.second);
  }

  private void startDiscovery(final @NonNull DiscoveryParams params) {
    final Intent intent = new Intent(this, DiscoveryActivity.class)
      .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
      .putExtra(IntentKey.DISCOVERY_PARAMS, params);
    startActivity(intent);
  }

  private void startProject(final @NonNull Project project) {
    final Intent intent = new Intent(this, ProjectActivity.class)
      .putExtra(IntentKey.PROJECT, project)
      .putExtra(IntentKey.REF_TAG, RefTag.thanks());
    startActivityWithTransition(intent, R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
  }

  private void startShare(final @NonNull Project project) {
    final Intent intent = new Intent(android.content.Intent.ACTION_SEND)
      .setType("text/plain")
      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
      .putExtra(Intent.EXTRA_TEXT, shareString(project) + " " + project.webProjectUrl());

    startActivity(Intent.createChooser(intent, shareThisProjectString));
  }

  private void startShareOnFacebook(final @NonNull Project project) {
    if (!ShareDialog.canShow(ShareLinkContent.class)) {
      return;
    }

    final Photo photo = project.photo();
    final ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
      .putString("og:type", "kickstarter:project")
      .putString("og:title", project.name())
      .putString("og:description", project.blurb())
      .putString("og:image", photo == null ? null : photo.small())
      .putString("og:url", project.webProjectUrl())
      .build();

    final ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
      .setActionType("kickstarter:back")
      .putObject("project", object)
      .build();

    final ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
      .setPreviewPropertyName("project")
      .setAction(action)
      .build();

    shareDialog.show(content);
  }

  private void startShareOnTwitter(final @NonNull Project project) {
    new TweetComposer.Builder(this)
      .text(shareString(project))
      .uri(Uri.parse(project.webProjectUrl()))
      .show();
  }
}
