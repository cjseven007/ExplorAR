package com.example.explorar.ui.home;

import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.ShapePath;

public class CrazyCornerTreatment extends CornerTreatment {

    @Override
    public void getCornerPath(
            ShapePath shapePath,
            float angle,
            float interpolation,
            float radius
    ) {

        float interpolatedRadius = radius * interpolation;
//        shapePath.reset(radius * interpolation, -radius * interpolation, 270f, 270 - angle);
//        shapePath.addArc(
//
//                -2* interpolatedRadius, // Adjusting the start X coordinate to move the arc leftwards
//                -2 * interpolatedRadius, // Adjusting the start Y coordinate to move the arc downwards
//                2 * interpolatedRadius,
//                0f,
//                180f,
//                -angle);

    }
}
