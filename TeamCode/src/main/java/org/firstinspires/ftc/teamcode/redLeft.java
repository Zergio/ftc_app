/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous
public class redLeft extends LinearOpMode {

    private DcMotor motor0;
    private DcMotor motor1;
    private DcMotor spoolMotor;

    private Servo servo0;
    private Servo servo1;
    private Servo servo2;
    private Servo servo3;
    private Servo servo5;

    LynxI2cColorRangeSensor color0;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    /*
     * Initialize the variables for the OpMode
     */
    private void initOpMode() {
        // Motors initialization
        motor0 = hardwareMap.get(DcMotor.class, "motor0");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spoolMotor = hardwareMap.get(DcMotor.class, "spoolMotor");
        // Servos initialization
        servo0 = hardwareMap.get(Servo.class, "servo0");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        servo5 = hardwareMap.get(Servo.class, "servo5");
        // Sensors intialization
        color0 = hardwareMap.get(LynxI2cColorRangeSensor.class, "color0");

        // initialize the spool encoder
        spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
     * Lift the clamp
     */
    private void lift(double height) {
        // variable height is the position of the slide from 0
        double targetHeight = -600f * height;
        spoolMotor.setTargetPosition((int)targetHeight);
        spoolMotor.setPower(1);
        while (spoolMotor.isBusy());
        spoolMotor.setPower(0);
        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /*
     * Open or close the clamp
     */
    private void clamp(boolean doClose)
    {
        if (doClose) {
            servo0.setPosition(1);
            servo1.setPosition(-1.5);
        } else {
            servo0.setPosition(0.25);
            servo1.setPosition(0.5);
        }
    }

    private float getSaturation(int color) {
        float[] array = new float[3];
        Color.colorToHSV(color, array);
        return array[1];
    }

    private float getHue(int color) {
        float[] array = new float[3];
        Color.colorToHSV(color, array);
        return array[0];
    }

    /*
     * Move the robot forward
     */
    private void moveInch(double inches) {
        motor0.setDirection(DcMotor.Direction.FORWARD);
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //  If you are using AndyMark or REV motors, replace
        // 1440 with
        //     AndyMark NeveRest 40: 1120
        //     REV Hex Motor: 2240
        motor0.setTargetPosition((int) (inches * -57.75));
        motor1.setTargetPosition((int) (inches * 57.75));
        waitForStart(); // really ???
        // the maximum speed of the motors.
        motor0.setPower(0.1);
        motor1.setPower(0.1);
        // Loop until both motors are no longer busy.
        while (motor0.isBusy() || motor1.isBusy());
        motor0.setPower(0);
        motor1.setPower(0);
    }

    private void turn(double degrees) {
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Change 1440 appropriately if you are not using
        // Modern Robotics encoders
        motor0.setTargetPosition((int)(degrees * 7.55));
        motor1.setTargetPosition((int)(degrees * 7.55));
        motor0.setPower(0.15);
        motor1.setPower(0.15);
        while (motor0.isBusy() || motor1.isBusy());
        motor0.setPower(0);
        motor1.setPower(0);
    }

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
            moveInch(-2.15);
        }
        //completely pick up servo
        servo5.setPosition(1);
        sleep(2000);
        // deposit glyph in safe zone
        moveInch(-32.3);
        sleep(200);
        turn(-90);
        sleep(200);
        lift(0);
        sleep(100);
        clamp(false);
        sleep(200);
        moveInch(8);
    }
}
