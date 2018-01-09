package edmt.dev.androidimagecropper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fenchtose.nocropper.CropperView;

public class MainActivity extends AppCompatActivity {

    Button btnCrop,btnToggleGesture;
    ImageView btnSnap,btnRotate;
    CropperView cropperView;
    Bitmap mBitmap;

    boolean isSnappedtoCenter=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        cropperView.setImageBitmap(originalBitmap);

        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImage();
            }
        });

        btnToggleGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = cropperView.isGestureEnabled();
                enabled = !enabled;
                cropperView.setGestureEnabled(enabled);
                Toast.makeText(getBaseContext(),"Gesture : "+(enabled?"Enabled":"Disabled"),Toast.LENGTH_SHORT).show();
            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSnappedtoCenter)
                    cropperView.cropToCenter();
                else
                    cropperView.fitToCenter();
                isSnappedtoCenter = !isSnappedtoCenter;

            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropperView.setImageBitmap(rotateBitmap(mBitmap,90));
            }
        });
    }

    private Bitmap rotateBitmap(Bitmap mBitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);
    }

    private void cropImage() {
        mBitmap = cropperView.getCroppedBitmap();
        if(mBitmap != null)
            cropperView.setImageBitmap(mBitmap);
    }

    private void initViews() {
        btnCrop = (Button)findViewById(R.id.btnCrop);
        btnToggleGesture = (Button)findViewById(R.id.btnToggleGesture);
        btnSnap = (ImageView)findViewById(R.id.snap_button);
        btnRotate = (ImageView)findViewById(R.id.rotate_button);
        cropperView = (CropperView)findViewById(R.id.imageView);
    }
}
