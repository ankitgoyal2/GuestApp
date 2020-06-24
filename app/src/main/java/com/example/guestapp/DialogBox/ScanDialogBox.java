package com.example.guestapp.DialogBox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.guestapp.R;

public class ScanDialogBox extends Dialog implements DialogInterface.OnClickListener {
    public ScanDialogBox(@NonNull Context context) {
        super(context);
        setContentView(R.layout.scan_dialog_box);
        TextView dismissText = findViewById(R.id.dismiss_text);
        dismissText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {


    }
}
