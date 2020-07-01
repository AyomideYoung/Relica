package com.zapsoftco.relica.util;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AnimationsCreator {
    public static Animation createSlideAnimationForNode(Node node) {
        TranslateTransition translate = new TranslateTransition();
        FadeTransition fade = new FadeTransition();

        translate.setFromX(0);
        translate.setToX(-300);
        translate.setDuration(Duration.millis(700));

        fade.setFromValue(1.0);
        fade.setToValue(0.08);
        fade.setDuration(Duration.millis(700));

        ParallelTransition transition = new ParallelTransition(node, fade, translate);

        transition.setInterpolator(Interpolator.EASE_IN);

        return transition;
    }
    public static Animation createLinedBackgroundErrorFlash(Region region) {
        Insets bottomInsets = new Insets( 0, 0, 2, 0);

        BackgroundFill orangeFill = new BackgroundFill(Color.web("f98934"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill redFill = new BackgroundFill(Color.web("f42525"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill whiteFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, bottomInsets);

        Background orangeLinedBackground = new Background(orangeFill, whiteFill);
        Background redLinedBackground = new Background(redFill, whiteFill);

        KeyValue kv1 = new KeyValue(region.backgroundProperty(), redLinedBackground);
        KeyValue kv2 = new KeyValue(region.backgroundProperty(), orangeLinedBackground);

        KeyFrame kf1 = new KeyFrame(Duration.millis(0), kv2);
        KeyFrame kf2 = new KeyFrame(Duration.millis(100), kv1);
        KeyFrame kf3 = new KeyFrame(Duration.millis(250), kv2);

        Timeline timeline = new Timeline(kf1, kf2, kf3);
        timeline.setCycleCount(4);
        timeline.setAutoReverse(false);

       return timeline;
    }

}
