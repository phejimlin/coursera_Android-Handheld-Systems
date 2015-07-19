package com.vino.phe.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String url = "http://www.moma.org";
    private DialogFragment mDialog;
    private GridView gridView;
    private SeekBar mSeekBar;
    private static final int ALERTTAG = 0;
    private int gridItems = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(this, gridItems));
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int length = gridView.getAdapter().getCount();
                for (int i = 0;i<length;i++) {
                    View gridItem = (View) gridView.getAdapter().getItem(i);
                    TextView textView = (TextView) gridItem.findViewById(R.id.grid_frame);
                    if (i % 8 == 0) {
                        textView.setBackgroundColor(Color.WHITE);
                    } else {
                        textView.setBackgroundColor(GridViewAdapter.getColorFromPosition(i, progress));
                    }
                    ((GridViewAdapter) gridView.getAdapter()).setItem(i,gridItem);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

    }
    // Show desired Dialog
    void showDialogFragment(){
        mDialog = new AlertDialogFragment();
        mDialog = AlertDialogFragment.newInstance();
        mDialog.show(getFragmentManager(), "Alert");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        android.support.v7.app.ActionBar mActionBar = this.getSupportActionBar();

            mActionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
            mActionBar.setTitle(R.string.actionbarTitle);
            mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.more_Information) {
            showDialogFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_Information)
                    .setPositiveButton(R.string.not_now, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog

                        }
                    })
                    .setNegativeButton(R.string.visit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!

                            Intent momaWebPageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(momaWebPageIntent);

                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
