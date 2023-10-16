package vn.edu.uef.dragdropdemo;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import vn.edu.uef.dragdropdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
    ImageView img, mImageView ;
    Button mChooseBtn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private static final int READ_EXTERNAL_STORAGE =1000;
    private static final String msg ="MainActivity";
    ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(msg,"onCreate invoked");
        img = findViewById(R.id.imageView);

        mImageView = findViewById(R.id.imageView);
        mChooseBtn = findViewById(R.id.addimage);

        //Handle button click
        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

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

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImage);
        }
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