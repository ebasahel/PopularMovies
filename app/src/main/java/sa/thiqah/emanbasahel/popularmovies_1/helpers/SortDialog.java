package sa.thiqah.emanbasahel.popularmovies_1.helpers;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import sa.thiqah.emanbasahel.popularmovies_1.R;

/**
 * Created by emanbasahel on 03/12/2017 AD.
 */

public class SortDialog {

    public interface onSortSelected {
        void onSortTypeSelected(String sortType);
    }

    private RadioButton radioTop, radioPopular,radioFavorites;
    private Button buttOk, buttCancel;
    private Context mContext;
    private onSortSelected sortSelected;

    public SortDialog(Context _Context) {
        mContext = _Context;
        sortSelected = (onSortSelected) mContext;
    }

    public Dialog createDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_sort);
        dialog.create();
        radioPopular = dialog.findViewById(R.id.sort_popular);
        radioTop = dialog.findViewById(R.id.sort_top);
        radioFavorites=dialog.findViewById(R.id.sort_favorites);
        buttOk = dialog.findViewById(R.id.button_ok);
        buttCancel = dialog.findViewById(R.id.button_cancdl);

        radioPopular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radioTop.setChecked(false);
            }
        });

        radioTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radioPopular.setChecked(false);
            }
        });


        buttOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (radioPopular.isChecked()) {
                    sortSelected.onSortTypeSelected(mContext.getString(R.string.popular_movie));

                } else if (radioTop.isChecked()) {
                    sortSelected.onSortTypeSelected(mContext.getString(R.string.toprated_movie));
                }else if (radioFavorites.isChecked())
                    sortSelected.onSortTypeSelected(mContext.getString(R.string.favorite_movie));

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
