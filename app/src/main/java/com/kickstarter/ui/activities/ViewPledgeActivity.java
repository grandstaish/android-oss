package com.kickstarter.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.Environment;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.models.Backing;
import com.kickstarter.ui.adapters.RewardsItemAdapter;
import com.kickstarter.viewmodels.ViewPledgeViewModel;
import com.squareup.picasso.Picasso;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromLeft;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

@RequiresActivityViewModel(ViewPledgeViewModel.class)
public final class ViewPledgeActivity extends BaseActivity<ViewPledgeViewModel> {
  protected ImageView avatarImageView;
  protected TextView backerNameTextView;
  protected TextView backerNumberTextView;
  protected TextView backingAmountAndDateTextView;
  protected TextView backingStatusTextView;
  protected TextView projectContextCreatorNameTextView;
  protected ImageView projectContextPhotoImageView;
  protected TextView projectContextProjectNameTextView;
  protected View projectContextView;
  protected TextView rewardMinimumAndDescriptionTextView;
  protected RecyclerView rewardsItemRecyclerView;
  protected View rewardsItemSection;
  protected TextView shippingAmountTextView;
  protected TextView shippingLocationTextView;
  protected View shippingSection;

  protected String backerNumberString;
  protected String backingStatusString;
  protected String pledgeAmountPledgeDateString;
  protected String rewardAmountRewardDescriptionString;
  protected String creatorNameString;
  protected String statusCanceled;
  protected String statusCollected;
  protected String statusDropped;
  protected String statusErrored;
  protected String statusPledged;

  private KSString ksString;

  @Override
  public void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_pledge_layout);
    final RewardsItemAdapter rewardsItemAdapter = new RewardsItemAdapter();
    rewardsItemRecyclerView.setAdapter(rewardsItemAdapter);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rewardsItemRecyclerView.setLayoutManager(layoutManager);

    final Environment environment = environment();
    ksString = environment.ksString();

    RxView.clicks(projectContextView)
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(__ -> viewModel.inputs.projectClicked());

    viewModel.outputs.backerNameTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(backerNameTextView::setText);

    viewModel.outputs.backerNumberTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(this::setBackerNumberTextViewText);

    viewModel.outputs.backingAmountAndDateTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(ad -> setBackingAmountAndDateTextViewText(ad.first, ad.second));

    viewModel.outputs.backingStatus()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(this::setBackingStatusTextViewText);

    viewModel.outputs.creatorNameTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(this::setCreatorNameTextViewText);

    viewModel.outputs.goBack()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(__ -> back());

    viewModel.outputs.loadBackerAvatar()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(this::loadBackerAvatar);

    viewModel.outputs.loadProjectPhoto()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(url -> Picasso.with(this).load(url).into(projectContextPhotoImageView));

    viewModel.outputs.projectNameTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(projectContextProjectNameTextView::setText);

    viewModel.outputs.rewardsItems()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(rewardsItemAdapter::rewardsItems);

    viewModel.outputs.rewardsItemsAreHidden()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(ViewUtils.setGone(rewardsItemSection));

    viewModel.outputs.rewardMinimumAndDescriptionTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(md -> setRewardMinimumAndDescriptionTextViewText(md.first, md.second));

    viewModel.outputs.shippingAmountTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(shippingAmountTextView::setText);

    viewModel.outputs.shippingLocationTextViewText()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(shippingLocationTextView::setText);

    viewModel.outputs.shippingSectionIsHidden()
      .compose(bindToLifecycle())
      .observeOn(mainThread())
      .subscribe(ViewUtils.setGone(shippingSection));
  }

  private void loadBackerAvatar(final @NonNull String url) {
    Picasso.with(this).load(url)
      .transform(new CircleTransformation())
      .into(avatarImageView);
  }

  private void setBackingAmountAndDateTextViewText(final @NonNull String amount, final @NonNull String date) {
    backingAmountAndDateTextView.setText(ksString.format(
      pledgeAmountPledgeDateString,
      "pledge_amount", amount,
      "pledge_date", date
    ));
  }

  private void setBackerNumberTextViewText(final @NonNull String sequence) {
    backerNumberTextView.setText(ksString.format(
      backerNumberString,
      "backer_number", sequence
    ));
  }

  private void setBackingStatusTextViewText(final @NonNull String status) {
    final String str;
    switch (status) {
      case Backing.STATUS_CANCELED:
        str = statusCanceled;
        break;
      case Backing.STATUS_COLLECTED:
        str = statusCollected;
        break;
      case Backing.STATUS_DROPPED:
        str = statusDropped;
        break;
      case Backing.STATUS_ERRORED:
        str = statusErrored;
        break;
      case Backing.STATUS_PLEDGED:
        str = statusPledged;
        break;
      default:
        str = "";
    }

    backingStatusTextView.setText(ksString.format(
      backingStatusString,
      "backing_status", str
    ));

  }

  private void setCreatorNameTextViewText(final @NonNull String name) {
    projectContextCreatorNameTextView.setText(ksString.format(
      creatorNameString,
      "creator_name", name
    ));
  }

  private void setRewardMinimumAndDescriptionTextViewText(final @NonNull String minimum, final @NonNull String description) {
    rewardMinimumAndDescriptionTextView.setText(ksString.format(
      rewardAmountRewardDescriptionString,
      "reward_amount", minimum,
      "reward_description", description
    ));
  }

  protected @Nullable Pair<Integer, Integer> exitTransition() {
    return slideInFromLeft();
  }
}
