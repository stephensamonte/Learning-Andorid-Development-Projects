package app.com.klexos.wakefield.news;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class AppRater {
    private final static int DAYS_STREAK_UNTIL_PROMPT = 4;//Min number of days
    private final static int DAYS_STREAK_RESET = 5;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 45;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences
                (mContext.getString(R.string.AppRater_preference_key), 0);
        if (prefs.getBoolean(mContext.getString(R.string.AppRater_dont_show_again_key), false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong
                (mContext.getString(R.string.AppRater_launch_count_key), 0) + 1;
        editor.putLong(mContext.getString(R.string.AppRater_launch_count_key), launch_count);

        // Get date of first launch
        Long date_LastLaunch = prefs.getLong
                (mContext.getString(R.string.AppRater_date_last_launch_key), 0);
        if (date_LastLaunch == 0) {
            date_LastLaunch = System.currentTimeMillis();
            editor.putLong(mContext.getString(R.string.AppRater_date_last_launch_key),
                    date_LastLaunch);
        }

        // reset after 8 days
        if (System.currentTimeMillis() >=
                (DAYS_STREAK_RESET * 24 * 60 * 60 * 1000) + date_LastLaunch) {
            // reset launch count
            editor.putLong(mContext.getString(R.string.AppRater_launch_count_key), 0);

            // set the date to the current date
            editor.putLong(mContext.getString(R.string.AppRater_date_last_launch_key),
                    System.currentTimeMillis());
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_LastLaunch +
                    (DAYS_STREAK_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showAskDialog(mContext, editor);
            }
        }
        editor.apply();
    }

    public static void showAskDialog
            (final Context mContext, final SharedPreferences.Editor editor) {

        // Logs "Ask If Enjoying Popup" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("Ask If Enjoying Popup"));

        // Enjoying application question
        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder1.setMessage(R.string.AppRater_showDialog_question);

        // Add the buttons
        builder1.setPositiveButton(R.string.AppRater_showDialog_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                if (editor != null) {
                    editor.putBoolean(mContext.getString(R.string.AppRater_dont_show_again_key),
                            true);
                    editor.commit();
                }
                showRateDialog(mContext);
            }
        });

        builder1.setNegativeButton(R.string.AppRater_showDialog_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                if (editor != null) {
                    editor.putBoolean(mContext.getString(R.string.AppRater_dont_show_again_key),
                            true);
                    editor.commit();
                }
                showHateDialog(mContext);
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder1.create();
        dialog.show();
    }

    public static void showRateDialog(final Context mContext) {

        // Logs "Ask For Rate Popup" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("Ask For Rate Popup"));

        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.AppRater_rate_question);

        // Add the buttons
        builder.setPositiveButton(R.string.AppRater_rate_hate_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                String packageName = mContext.getApplicationContext().getPackageName();

                mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mContext.getResources().getString(R.string.app_download_website)
                                + packageName)));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.AppRater_rate_hate_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showHateDialog(final Context mContext) {

        // Logs "Ask For Feedback Popup" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("Ask For Feedback Popup"));

        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.AppRater_hate_question);

        // Add the buttons
        builder.setPositiveButton(R.string.AppRater_rate_hate_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + // only email apps should handle this
                        mContext.getResources().getString(R.string.app_help_emailTo)));
                intent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().
                        getString(R.string.app_help_subject));
                intent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().
                        getString(R.string.app_email_message));
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            }
        });

        builder.setNegativeButton(R.string.AppRater_rate_hate_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}