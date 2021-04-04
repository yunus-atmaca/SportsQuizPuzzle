package com.sportsquizpuzzle.listener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.sportsquizpuzzle.R;
import com.sportsquizpuzzle.puzzle.Piece;

public class DragListener implements View.OnDragListener {

    private static final String TAG = "Drag-Listener";

    private View draggableObj;
    private Piece[] pieces;
    private boolean isDropped;

    private final Drawable highlightedShape;
    private final Drawable normalShape;

    private View highlightedView;

    private int completedPieces;
    private OnCompleteListener listener;

    public DragListener(Context context, Piece[] pieces, View view, OnCompleteListener listener) {
        this.pieces = pieces;
        this.isDropped = false;

        highlightedShape = ContextCompat.getDrawable(context, R.drawable.highlighted_shape);
        normalShape = ContextCompat.getDrawable(context, R.drawable.normal_shape);

        highlightedView = view;

        this.completedPieces = 0;
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "ACTION_DRAG_STARTED");
                highlightedView.setBackground(highlightedShape);
                this.isDropped = false;
                draggableObj = (View) dragEvent.getLocalState();
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                highlightedView.setBackground(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                Log.d(TAG, "ACTION_DROP");
                Piece p = findPiece(draggableObj.getId());
                if (p != null) {
                    if (view.getId() == p.getTargetHolderId()) {
                        this.isDropped = true;
                        this.completedPieces +=1;
                        RelativeLayout targetHolder = (RelativeLayout) view;
                        targetHolder.findViewById(p.getTargetImgId()).setVisibility(View.VISIBLE);
                    } else {
                        this.isDropped = false;
                    }
                } else {
                    this.isDropped = false;
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG, "ACTION_DRAG_ENDED");
                highlightedView.setBackground(normalShape);

                if (isDropped) {
                    draggableObj.setVisibility(View.INVISIBLE);
                } else {
                    draggableObj.setVisibility(View.VISIBLE);
                }

                if(this.completedPieces == pieces.length){
                    this.listener.onComplete();
                }
                break;
            default:
                break;
        }

        return true;
    }

    private Piece findPiece(int id) {
        for (Piece p : pieces) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}
