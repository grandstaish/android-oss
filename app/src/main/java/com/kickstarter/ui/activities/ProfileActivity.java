package com.kickstarter.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.ApiCapabilities;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.RecyclerViewPaginator;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.libs.utils.ApplicationUtils;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.Project;
import com.kickstarter.models.User;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.ui.adapters.ProfileAdapter;
import com.kickstarter.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.IntegerUtils.isNonZero;

@RequiresActivityViewModel(ProfileViewModel.class)
public final class ProfileActivity extends BaseActivity<ProfileViewModel> {
  private ProfileAdapter adapter;
  private RecyclerViewPaginator paginator;

  protected ImageView avatarImageView;
  protected TextView userNameTextView;
  protected TextView createdNumTextView;
  protected TextView backedNumTextView;
  protected TextView createdTextView;
  protected TextView backedTextView;
  protected View dividerView;
  protected RecyclerView recyclerView;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile_layout);

    adapter = new ProfileAdapter(viewModel);
    final int spanCount = ViewUtils.isLandscape(this) ? 3 : 2;
    recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    recyclerView.setAdapter(adapter);

    paginator = new RecyclerViewPaginator(recyclerView, viewModel.inputs::nextPage);

    viewModel.outputs.user()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::setViews);

    viewModel.outputs.projects()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::loadProjects);

    viewModel.outputs.showProject()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::startProjectActivity);

    viewModel.outputs.showDiscovery()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> startDiscoveryActivity());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    paginator.stop();
    recyclerView.setAdapter(null);
  }

  private void loadProjects(final @NonNull List<Project> projects) {
    if (projects.size() == 0) {
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setPadding(0, recyclerView.getPaddingTop(), recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
      if (ViewUtils.isPortrait(this)) {
        disableNestedScrolling();
      }
    }

    adapter.takeProjects(projects);
  }

  @TargetApi(21)
  private void disableNestedScrolling() {
    if (ApiCapabilities.canSetNestingScrollingEnabled()) {
      recyclerView.setNestedScrollingEnabled(false);
    }
  }

  private void setViews(final @NonNull User user) {
    Picasso.with(this).load(user.avatar()
      .medium())
      .transform(new CircleTransformation())
      .into(avatarImageView);

    userNameTextView.setText(user.name());

    final Integer createdNum = user.createdProjectsCount();
    if (isNonZero(createdNum)) {
      createdNumTextView.setText(String.valueOf(createdNum));
    } else {
      createdTextView.setVisibility(View.GONE);
      createdNumTextView.setVisibility(View.GONE);
      dividerView.setVisibility(View.GONE);
    }

    final Integer backedNum = user.backedProjectsCount();
    if (isNonZero(backedNum)) {
      backedNumTextView.setText(String.valueOf(backedNum));
    } else {
      backedTextView.setVisibility(View.GONE);
      backedNumTextView.setVisibility(View.GONE);
      dividerView.setVisibility(View.GONE);
    }
  }

  private void startDiscoveryActivity() {
    ApplicationUtils.resumeDiscoveryActivity(this);
  }

  private void startProjectActivity(final @NonNull Project project) {
    final Intent intent = new Intent(this, ProjectActivity.class)
      .putExtra(IntentKey.PROJECT, project);
    startActivityWithTransition(intent, R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
  }
}
