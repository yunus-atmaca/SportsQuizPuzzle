package com.sportsquizpuzzle.customViews;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sportsquizpuzzle.R;

import java.util.Objects;

public class Modal extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "Modal-Fragment";

    private final String message;

    private final ModalListener listener;

    public Modal(String message, ModalListener listener){
        this.message = message;
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setWindowAnimations(R.style.anim_fade);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.modal, container, false);

        root.findViewById(R.id.yes).setOnClickListener(this);
        root.findViewById(R.id.no).setOnClickListener(this);

        ((TextView)root.findViewById(R.id.message)).setText(this.message);

        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.yes){
            yesClicked();
        }else if(view.getId() == R.id.no){
            noClicked();
        }else{
            Log.d(TAG, "Unimplemented call");
        }
    }

    private void yesClicked(){
        listener.onModalResult(true);

        dismiss();
        onDestroy();
    }

    private void noClicked(){
        listener.onModalResult(false);

        dismiss();
        onDestroy();
    }

    public interface ModalListener{
        void onModalResult(boolean res);
    }
}
