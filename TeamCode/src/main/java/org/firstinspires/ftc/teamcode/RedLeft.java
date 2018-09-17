package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Autonomous OpMode for the case where we are on the red team on the left side of the field
 */
@Autonomous
public class RedLeft extends AutonomousOpMode {

    @Override
    public void runOpMode() {

        initOpMode();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        double column = getColumn();

        // position color servo down
        raise(false);
        sleep(1000); // We sleep to make sure that the original command is executed.
        int currentColor = Color.rgb(color0.red(), color0.green(), color0.blue());
        // test for blue
        if (isBlue()) {
            //Pick up servo a bit and then move backwards to knock of jewel
            raise(false);
            knock("sensor");
        } else {
            raise(false);
            knock("nonsensor");
        }
        //completely pick up servo
        raise(true);
        sleep(1000);
        // deposit glyph in safe zone
        moveInch(-4.8,true);
        sleep(100);
        moveInch(-30 + column,false);
        sleep(200);
        turn(-90);
        sleep(100);
        moveInch(8,false);
        sleep(200);
        pull(false);
        moveInch(1,false);
        moveInch(-2,false);

        // Test for getting more glyphs
//        pull(true);
//        turn(180);
//        moveInch(45);
//        moveInch(-10);
//        turn(180);
//        lift(.8);
//        moveInch(38);
//        pull(false);
//        moveInch(3);
//        moveInch(-3);
    }

}