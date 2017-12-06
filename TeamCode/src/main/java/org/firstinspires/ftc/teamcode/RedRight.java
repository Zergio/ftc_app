package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous OpMode for the case where we are on the red team on the right side of the field
 */
@Autonomous
public class RedRight extends BaseAutonomous {

    @Override
    public void runOpMode() {
        initOpMode();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        clamp(true);
        sleep(500);
        // move a clamp up a little bit more than halfway
        lift(0.6);
        // set color servo down
        servo5.setPosition(0.5);
        servo5.setPosition(0.065);
        sleep(2000); // ???
        int currentColor = Color.rgb(color0.red(), color0.green(), color0.blue());
        // test for blue
        if (getSaturation(currentColor) >= 0.5
                && getHue(currentColor) > 190 && getHue(currentColor) < 250) {
            //Pick up servo a bit and then move backwards to knock of jewel
            servo5.setPosition(0.085);
            moveInch(-1.8);
        } else {
            servo5.setPosition(0.085);
            moveInch(2);
            servo5.setPosition(1);
            moveInch(-2.2);
        }
        //completely pick up servo
        servo5.setPosition(1);
        sleep(2000);
        // deposit glyph in safe zone
        moveInch(-21);
        sleep(200);
        turn(90);
        sleep(200);
        moveInch(11);
        sleep(200);
        turn(90);
        sleep(200);
        lift(0);
        sleep(200);
        clamp(false);
        sleep(200);
        moveInch(10);
    }
}