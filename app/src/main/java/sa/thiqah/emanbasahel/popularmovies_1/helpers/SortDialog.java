package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import sa.thiqah.emanbasahel.popularmovies_1.R;

/**
 * Created by emanbasahel on 03/12/2017 AD.
 */

public class SortDialog extends DialogFragment {

    RadioButton radioTop,radioPopular;
    boolean isTopRated=false, isPopular=false;
    Button buttOk, buttCancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_sort, container, false);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        radioPopular=dialog.findViewById(R.id.sort_popular);
        radioTop=dialog.findViewById(R.id.sort_top);
        buttOk = dialog.findViewById(R.id.button_ok);
        buttCancel = dialog.findViewById(R.id.button_cancdl);

        if (radioPopular.isChecked())
        {
            radioTop.setChecked(false);
            isTopRated=false;
            isPopular=true;
        }
        else if (radioTop.isChecked())
        {
            radioPopular.setChecked(false);
            isTopRated=true;
            isPopular=false;
        }

        buttOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isTopRated)
                {
                    Log.d("Sort Value","toRated");
                }else if (isPopular)
                {
                    Log.d("Sort Value","Popular");
                }

                dialog.dismiss();
            }
        });

        buttCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
