package in.motivation.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import in.motivation.R;
/*
Dialog to show error message
 */

public class ErrorDialog {
    String message;
    Context context;

    /*
       @message - takes error message
       @context - context
     */
   public   ErrorDialog(String message, Context context)
    {
        this.message=message;
        this.context=context;
    }


    public void showLoader() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setPositiveButton(
                "Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  context.finish();
                        System.exit(0);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
