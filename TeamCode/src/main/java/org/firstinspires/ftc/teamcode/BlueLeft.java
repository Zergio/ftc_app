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
 * Autonomous OpMode for the case where we are on the blue team on the left side of the field
 */
@Autonomous
public class BlueLeft extends AutonomousOpMode {

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
            knock("nonsensor");
        } else {
            raise(false);
            knock("sensor");
        }
        //completely pick up servo
        raise(true);
        sleep(2000);
        // deposit glyph in safe zone
        moveInch(4,true);
        sleep(100);
        moveInch(24,false);
        sleep(200);
        turn(90);
        sleep(200);
        moveInch(13 + column,false);
        sleep(200);
        turn(-90);
        sleep(200);
        moveInch(2,false);
        sleep(200);
        pull(false);
        moveInch(-3,false);
        moveInch(3,false);
        moveInch(-3,false);
    }

}