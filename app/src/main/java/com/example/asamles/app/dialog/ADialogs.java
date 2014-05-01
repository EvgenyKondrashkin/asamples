package com.example.asamles.app.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.utils.ImageCheckboxListAdapter;
import com.example.asamles.app.dialog.utils.ImageTextCheckbox;

import java.util.ArrayList;
import java.util.Calendar;

public class ADialogs {
    private Context context;
    private ADialogsListener aListener = null;
    private ADialogsTimeListener timeListener = null;
    private ADialogsProgressListener progressListener = null;
    private ADialogsSeekBarListener seekbarListener = null;
    private ADialogsCustomListListener listListener = null;

    public ADialogs(Context context) {
        this.context = context;
    }

    private AlertDialog.Builder build(boolean cancelable, String title, String message) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        if (title != null) {
            ad.setTitle(title);
        }
        if (message != null) {
            ad.setMessage(message);
        }
        if (cancelable) {
            ad.setCancelable(true);
        }
        return ad;
    }

    // TimeDialog
    public interface ADialogsTimeListener {
        public void onADialogsTimePositiveClick(DialogInterface dialog, TimePicker tp, CheckBox active);

        public void onADialogsTimeNegativeClick(DialogInterface dialog);

        public void onADialogsTimeCancel(DialogInterface dialog);
    }

    public void setADialogsTimeListener(ADialogsTimeListener timeListener) {
        this.timeListener = timeListener;
    }

    public void openTime(boolean cancelable, String title, String positiveButton, String negativeButton) {
        final TimePicker tp;
        final CheckBox active;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View timeLayout = inflater.inflate(R.layout.dialog_time, null);
        tp = (TimePicker) timeLayout.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        tp.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        active = (CheckBox) timeLayout.findViewById(R.id.active);
        AlertDialog.Builder ad = build(cancelable, title, null);
        ad.setView(timeLayout);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (timeListener != null) {
                        timeListener.onADialogsTimePositiveClick(dialog, tp, active);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    if (timeListener != null) {
                        timeListener.onADialogsTimeNegativeClick(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    if (timeListener != null) {
                        timeListener.onADialogsTimeCancel(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        ad.create().show();
    }

    //AlertDialog
    //DialogsMain, BlurBackground, ImageEditMain, PicassoMain, BlurredCustomDialog
    public interface ADialogsListener {
        public void onADialogsPositiveClick(DialogInterface dialog);

        public void onADialogsNegativeClick(DialogInterface dialog);

        public void onADialogsCancel(DialogInterface dialog);
    }

    public void setADialogsListener(ADialogsListener aListener) {
        this.aListener = aListener;
    }

    public void alert(boolean cancelable, String title, String message, String positiveButton, String negativeButton) {
        AlertDialog.Builder ad = build(cancelable, title, message);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (aListener != null) {
                        aListener.onADialogsPositiveClick(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (aListener != null) {
                        aListener.onADialogsNegativeClick(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    if (aListener != null) {
                        aListener.onADialogsCancel(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        ad.create().show();
    }

    // ProgressDialog
    private ProgressDialog pd;
    public interface ADialogsProgressListener {
        public void onADialogsProgressCancel(DialogInterface dialog);
    }

    public void setADialogsTimeListener(ADialogsProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public void progress(boolean cancelable, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(cancelable);
        pd.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    if (progressListener != null) {
                        progressListener.onADialogsProgressCancel(dialog);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
    }
    public void showProgress(){
        pd.show();
    }
    public void cancelProgress(){
        pd.dismiss();
    }
    //SeekbarDialog
    public interface ADialogsSeekBarListener {
        public void onADialogsSeekBarPositiveClick(DialogInterface dialog, SeekBar seekbar);
    }

    public void setADialogsSeekBarListener(ADialogsSeekBarListener seekbarListener) {
        this.seekbarListener = seekbarListener;
    }

    public void seekbar(boolean cancelable, String title, int progress, String positiveButton, String negativeButton) {
        final SeekBar seekBar;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View seekLayout = inflater.inflate(R.layout.dialog_seekbar, null);
        seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
        seekBar.setProgress(progress);
        AlertDialog.Builder ad = build(cancelable, title, null);
        ad.setView(seekLayout);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (seekbarListener != null) {
                        seekbarListener.onADialogsSeekBarPositiveClick(dialog, seekBar);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        ad.create().show();
    }

    // CustomListDialog
    public interface ADialogsCustomListListener {
        public void onADialogsCustomListPositiveClick(DialogInterface dialog, ArrayList<ImageTextCheckbox> list);
    }

    public void setADialogsCustomListListener(ADialogsCustomListListener listListener) {
        this.listListener = listListener;
    }

    public void customList(Context context, boolean cancelable, String title, final ArrayList<ImageTextCheckbox> list, String positiveButton, String negativeButton) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listLayout = inflater.inflate(R.layout.dialog_custom_list, null);

        ListView listMenu = (ListView) listLayout.findViewById(R.id.listMenu);
        final ImageCheckboxListAdapter adapter = new ImageCheckboxListAdapter(context, list);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getCheck()) {
                    list.get(position).setCheck(false);
                } else {
                    list.get(position).setCheck(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        listMenu.setAdapter(adapter);
        AlertDialog.Builder ad = build(cancelable, title, null);
        ad.setView(listLayout);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (listListener != null) {
                        listListener.onADialogsCustomListPositiveClick(dialog, list);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        ad.create().show();
    }
}
