package com.neos.trackandroll.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.view.activity.AbstractActivity;
import com.neos.trackandroll.view.activity.AbstractDataXXXActivity;
import com.neos.trackandroll.view.activity.PlayersActivity;

import java.util.Calendar;

public class DialogUtils {

    public static void displayDialogSureToRemoveSelectedPlayers(final PlayersActivity playersActivity) {
        new MaterialDialog.Builder(playersActivity)
                .title(R.string.dialog_delete_selected_players_title)
                .content(R.string.dialog_delete_selected_players_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        playersActivity.removeSelectedPlayers();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogSureToQuitApp(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_quit_app_title)
                .content(R.string.dialog_quit_app_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.quitApplication();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogSureToRemovePlayer(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_delete_player_title)
                .content(R.string.dialog_delete_player_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.removePlayer();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogSetNameSession(final AbstractActivity abstractActivity, String filterName) {

        MaterialDialog dialog = new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_set_name_session_title)
                .customView(R.layout.dialog_set_name_session_content, false)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .build();

        final EditText etSetSessionName = (EditText) dialog.findViewById(R.id.etSetSessionName);
        etSetSessionName.setText(filterName);

        dialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (!etSetSessionName.getText().toString().isEmpty()
                        && !DataStore.getInstance().getAppConfiguration().getSessionNameList().contains(etSetSessionName.getText().toString())) {
                    abstractActivity.setSessionName(etSetSessionName.getText().toString());
                }
                dialog.dismiss();
            }
        }).show();
    }

    public static MaterialDialog displayDialogSavingRunningSession(final AbstractActivity abstractActivity) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_saving_session_title)
                .customView(R.layout.dialog_saving_session_content, false)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)
                .build();

        ProgressBar pcSavingAuth = (ProgressBar) materialDialog.findViewById(R.id.pcSavingSession);
        pcSavingAuth.getIndeterminateDrawable().setColorFilter(abstractActivity.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        materialDialog.show();
        return materialDialog;
    }

    public static void displayDialogNameRunningSession(final AbstractActivity abstractActivity, final Calendar endInstant) {

        MaterialDialog dialog = new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_name_running_session_title)
                .customView(R.layout.dialog_name_running_session_content, false)
                .cancelable(false)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .positiveText(R.string.dialog_save)
                .build();

        final EditText etSetSessionName = (EditText) dialog.findViewById(R.id.etSetSessionName);

        // TODO maybe custom default session name
        etSetSessionName.setText(DataUtils.getDefaultSessionName(endInstant));

        dialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (!etSetSessionName.getText().toString().isEmpty()
                        && !DataStore.getInstance().getAppConfiguration().getSessionNameList().contains(etSetSessionName.getText().toString())) {
                    abstractActivity.addNewSession(etSetSessionName.getText().toString());
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(abstractActivity, abstractActivity.getResources().getString(R.string.error_session_name), Toast.LENGTH_SHORT).show();
                    DialogUtils.displayDialogNameRunningSession(abstractActivity, endInstant);
                }
            }
        }).show();
    }

    public static void displayDialogDeleteSession(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_delete_session_title)
                .content(R.string.dialog_delete_session_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.removeSession();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogDeletePlayerData(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_delete_player_data_title)
                .content(R.string.dialog_delete_player_data_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.removePlayerSession();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogCannotRemoveGlobalSession(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_cannot_delete_session_title)
                .content(R.string.dialog_cannot_delete_session_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .positiveText(R.string.dialog_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogCannotCustomGlobalSession(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_cannot_custom_session_title)
                .content(R.string.dialog_cannot_custom_session_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .positiveText(R.string.dialog_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogDeleteRunningSession(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_delete_running_session_title)
                .content(R.string.dialog_delete_running_session_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.deleteRunningSession();
                    }
                })

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void displayDialogEndSession(final AbstractActivity abstractActivity) {
        new MaterialDialog.Builder(abstractActivity)
                .title(R.string.dialog_end_session_title)
                .content(R.string.dialog_end_session_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .neutralText(R.string.dialog_delete)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        DialogUtils.displayDialogDeleteRunningSession(abstractActivity);
                    }
                })

                .positiveText(R.string.dialog_save)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractActivity.stopRunningSessionTimeForSaving();
                    }
                })
                .show();
    }

    public static void displayDialogSureToRemovePlayerData(final AbstractDataXXXActivity abstractDataXXXActivity) {
        new MaterialDialog.Builder(abstractDataXXXActivity)
                .title(R.string.dialog_delete_player_data_title)
                .content(R.string.dialog_delete_player_data_content)
                .cancelable(true)
                .backgroundColorRes(R.color.colorDialogBackground)
                .contentColor(Color.WHITE)
                .titleColorRes(R.color.colorDialogTitle)
                .positiveColorRes(R.color.colorDialogButton)
                .neutralColorRes(R.color.colorDialogButton)
                .negativeColorRes(R.color.colorDialogButton)

                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .positiveText(R.string.dialog_confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        abstractDataXXXActivity.removePlayerData();
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
