package com.dummies.tasks.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Rudi on 22.03.2017.
 */

public class SaveAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity());
        builder.setMessage("Soll der Termin gespeichert werden?")
                .setTitle("Sind sie sicher?")
                .setCancelable(false)
                .setPositiveButton("Ja",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Aktionen durchführen
                                System.out.println("Hihi, speichern :D");
                            }
                        })
                .setNegativeButton("Nein",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }

}
