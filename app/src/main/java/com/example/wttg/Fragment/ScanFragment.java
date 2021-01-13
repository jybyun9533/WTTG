package com.example.wttg.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.wttg.CameraActivity;
import com.example.wttg.R;

public class ScanFragment extends Fragment {

    Context context;

    Button btn_camera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_scan, container, false);
        btn_camera = view.findViewById(R.id.btn_camera);

        context = container.getContext();
        btn_camera.setText("카메라");

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }

}
