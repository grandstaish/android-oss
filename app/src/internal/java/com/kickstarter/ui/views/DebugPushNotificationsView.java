package com.kickstarter.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.DeviceRegistrarType;
import com.kickstarter.libs.PushNotifications;
import com.kickstarter.models.pushdata.Activity;
import com.kickstarter.models.pushdata.GCM;
import com.kickstarter.services.apiresponses.PushNotificationEnvelope;

import javax.inject.Inject;

public final class DebugPushNotificationsView extends ScrollView {
  protected @Inject DeviceRegistrarType deviceRegistrar;
  protected @Inject PushNotifications pushNotifications;

  private static final String PROJECT_PHOTO = "https://ksr-ugc.imgix.net/projects/1176555/photo-original.png?v=1407175667&w=120&h=120&fit=crop&auto=format&q=92&s=2065d33620d4fef280c4c2d451c2fa93";
  private static final String USER_PHOTO = "https://ksr-ugc.imgix.net/avatars/1583412/portrait.original.png?v=1330782076&w=120&h=120&fit=crop&auto=format&q=92&s=a9029da56a3deab8c4b87818433e3430";
  private static final Long PROJECT_ID = 1761344210L;

  public DebugPushNotificationsView(final @NonNull Context context) {
    this(context, null);
  }

  public DebugPushNotificationsView(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DebugPushNotificationsView(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    if (isInEditMode()) {
      return;
    }

    ((KSApplication) getContext().getApplicationContext()).component().inject(this);
  }

  public void registerDeviceButtonClick() {
    deviceRegistrar.registerDevice();
  }

  public void unregisterDeviceButtonClick() {
    deviceRegistrar.unregisterDevice();
  }

  public void simulateFriendBackingButtonClick() {
    final GCM gcm = GCM.builder()
      .title("Check it out")
      .alert("Christopher Wright backed SKULL GRAPHIC TEE.")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_BACKING)
      .id(1)
      .projectId(PROJECT_ID)
      .projectPhoto(PROJECT_PHOTO)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateFriendFollowButtonClick() {
    final GCM gcm = GCM.builder()
      .title("You're in good company")
      .alert("Christopher Wright is following you on Kickstarter!")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_FOLLOW)
      .id(2)
      .userPhoto(USER_PHOTO)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateProjectCancellationButtonClick() {
    final GCM gcm = GCM.builder()
      .title("Kickstarter")
      .alert("SKULL GRAPHIC TEE has been canceled.")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_CANCELLATION)
      .id(3)
      .projectId(PROJECT_ID)
      .projectPhoto(PROJECT_PHOTO)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateProjectFailureButtonClick() {
    final GCM gcm = GCM.builder()
      .title("Kickstarter")
      .alert("SKULL GRAPHIC TEE was not successfully funded.")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_FAILURE)
      .id(4)
      .projectId(PROJECT_ID)
      .projectPhoto(PROJECT_PHOTO)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateProjectLaunchButtonClick() {
    final GCM gcm = GCM.builder()
      .title("Want to be the first backer?")
      .alert("Taylor Moore just launched a project!")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_LAUNCH)
      .id(5)
      .projectId(PROJECT_ID)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateProjectReminderButtonClick() {
    final GCM gcm = GCM.builder()
      .title("Last call")
      .alert("Reminder! SKULL GRAPHIC TEE is ending soon.")
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder()
      .gcm(gcm)
      .project(PushNotificationEnvelope.Project.builder().id(PROJECT_ID).photo(PROJECT_PHOTO).build())
      .build();
    pushNotifications.add(envelope);
  }

  public void simulateProjectSuccessButtonClick() {
    pushNotifications.add(projectSuccessEnvelope());
  }

  public void simulateProjectUpdateButtonClick() {
    final GCM gcm = GCM.builder()
      .title("News from Taylor Moore")
      .alert("Update #1 posted by SKULL GRAPHIC TEE.")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_UPDATE)
      .id(7)
      .projectId(PROJECT_ID)
      .projectPhoto(PROJECT_PHOTO)
      .updateId(1033848L)
      .build();

    final PushNotificationEnvelope envelope = PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
    pushNotifications.add(envelope);
  }

  public void simulateBurstClick() {
    final PushNotificationEnvelope baseEnvelope = projectSuccessEnvelope();
    for (int i = 0; i < 100; i++) {
      // Create a different signature for each push notification
      final GCM gcm = baseEnvelope.gcm().toBuilder().alert(Integer.toString(i)).build();
      pushNotifications.add(baseEnvelope.toBuilder().gcm(gcm).build());
    }
  }

  private @NonNull PushNotificationEnvelope projectSuccessEnvelope() {
    final GCM gcm = GCM.builder()
      .title("Time to celebrate!")
      .alert("SKULL GRAPHIC TEE has been successfully funded.")
      .build();

    final Activity activity = Activity.builder()
      .category(com.kickstarter.models.Activity.CATEGORY_SUCCESS)
      .id(6)
      .projectId(PROJECT_ID)
      .projectPhoto(PROJECT_PHOTO)
      .build();

    return PushNotificationEnvelope.builder().activity(activity).gcm(gcm).build();
  }
}
