package vn.edu.uef.dragdropdemo;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import vn.edu.uef.dragdropdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
    ImageView img;
    private static final String msg ="MainActivity";
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(msg,"onCreate invoked");
        img = findViewById(R.id.imageView);
        constraintLayout = findViewById(R.id.container);
        constraintLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v , DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(msg , "Action is DragEvent.ACTION_DRAG_STARTED");
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg , "Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(msg , "Action is DragEvent.ACTION_DRAG_EXITED");

                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(msg , "Action is DragEvent.ACTION_DRAG_LOCATION");
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(msg , "Action is DragEvent.ACTION_DRAG_ENDED");
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d(msg , "ACTION_DROP event");
                        View tvState = (View) event.getLocalState();
                        Log.d(msg , "onDrag:viewX" + event.getX() + "viewY" + event.getY());
                        Log.d(msg , "onDrag: Owner->" + tvState.getParent());
                        ViewGroup tvParent = (ViewGroup) tvState.getParent();
                        tvParent.removeView(tvState);
                        ConstraintLayout container = (ConstraintLayout) v;
                        container.addView(tvState);
                        tvParent.removeView(tvState);
                        tvState.setX(event.getX());
                        tvState.setY(event.getY());
                        ((ConstraintLayout) v).addView(tvState);
                        v.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v , MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("" , "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);
                    v.startDrag(data , shadowBuilder , v , 0);
                    v.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg,"onStart invoked");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg,"onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg,"onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg,"onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(msg,"onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg,"onDestroy invoked");
    }
}