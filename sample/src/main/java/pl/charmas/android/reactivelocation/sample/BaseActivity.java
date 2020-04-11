package pl.charmas.android.reactivelocation.sample;

import android.Manifest;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        RxPermissions
                .getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            onLocationPermissionGranted();
                        } else {
                            Toast.makeText(BaseActivity.this, "Sorry, no demo without permission...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected abstract void onLocationPermissionGranted();
}
