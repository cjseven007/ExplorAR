package com.example.explorar.ar;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.explorar.MainActivity;
import com.example.explorar.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
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
    private String link = "https://firebasestorage.googleapis.com/v0/b/testingforvariousthings.appspot.com/o/thomas_the_tank_engine.glb?alt=media&token=8ab4b489-20f1-447e-83f3-5cad04a33c28";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aractivity);
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
        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
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
        transformableNode.getScaleController().setMaxScale(1f);
        transformableNode.getScaleController().setMinScale(0.5f);
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
