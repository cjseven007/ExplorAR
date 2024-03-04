package com.example.explorar.ui.ar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.explorar.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;

public class ARActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private ArSceneView arSceneView;

    private ModelRenderable modelRenderable;
    private Anchor centerAnchor;
    private String title;
    private String content;
    private boolean status;
    private float lowerBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aractivity);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        status = getIntent().getBooleanExtra("status", false);
        lowerBound = getIntent().getFloatExtra("lowerBound", 0.5f);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arSceneView = arFragment.getArSceneView();

        // Set clear background color for better scene visibility
        arSceneView.setBackgroundColor(Color.TRANSPARENT);

        setUpModel();
        setUpPlane();

        // Load the glb model asynchronously to optimize startup performance
        /*ModelRenderable.builder()
                .setSource(arSceneView.getContext(), R.raw.andy)
                .build()
                .thenAccept(modelRenderable -> {
                    // Create a Node to attach the model to
                    Node modelNode = new Node();
                    modelNode.setRenderable(modelRenderable);

                    AnchorNode anchorNode = new AnchorNode(centerAnchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.getScaleController().setMaxScale(1f);
                    transformableNode.getScaleController().setMinScale(0.5f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(modelRenderable);
                    arSceneView.getScene().addChild(anchorNode);
                    transformableNode.select();

                    transformableNode.setEnabled(true);

                })
                .exceptionally(throwable -> {
                    // Handle loading errors (optional)
                    return null;
                });*/

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/
    }

    private void setUpModel() {
        int ref = R.raw.andy;
        switch (content) {
            case "arduino_1":
                ref = R.raw.arduino_1;
                break;
            case "circuit_board_1":
                ref = R.raw.circuit_board_1;
                break;
        }
        ModelRenderable.builder()
                .setSource(this, ref)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(throwable -> {
                    return null;
                });
    }

    private void setUpPlane(){
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                createModel(anchorNode);
            }
        });
    }

    private void createModel(AnchorNode anchorNode){
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.getScaleController().setMaxScale(lowerBound+0.5f);
        transformableNode.getScaleController().setMinScale(lowerBound);
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arSceneView.getScene().addChild(anchorNode);
        transformableNode.select();

        transformableNode.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arSceneView != null) {
            try {
                arSceneView.resume();
            } catch (CameraNotAvailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (arSceneView != null) {
            arSceneView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (arSceneView != null) {
            arSceneView.destroy();
        }
    }
}
