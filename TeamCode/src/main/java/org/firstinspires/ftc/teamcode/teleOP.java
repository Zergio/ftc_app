package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ethan on 12/3/17.
 */

public class teleOP extends LinearOpMode {
    int slow = 2;

    private DcMotor motor0;
    private DcMotor motor1;
    private DcMotor spoolMotor;

    private Servo servo0;
    private Servo servo1;
    private Servo servo2;
    private Servo servo3;
    private Servo servo5;

    public void runOpMode() {
        waitForStart();

        while (opModeIsActive()){
            servo5.setPosition(1);
            drive();
            runToggleClamp();
            runSpool();
            relic();
        }
    }

    private void drive() {
        if (gamepad1.right_bumper) {
            slow = 1;
        }
        if (gamepad1.left_bumper) {
            slow = 2;
        }

        motor0.setPower(gamepad1.left_stick_y / slow);
        motor1.setPower(-1 * gamepad1.left_stick_y / slow);

        if (gamepad1.right_stick_x != 0) {
            motor0.setPower(gamepad1.right_stick_x / slow);
            motor1.setPower(gamepad1.right_stick_x / slow);
        }
    }

    private void runToggleClamp() {
        if (gamepad2.left_bumper) {
            servo0.setPosition(0.25);
            servo1.setPosition(0.5);
        }
        if (gamepad2.right_bumper) {
            servo0.setPosition(1);
            servo1.setPosition(-1.5);
        }
        if (gamepad2.a) {
            servo0.setPosition(0.5);
            servo1.setPosition(0.25);
        }
    }

    private void runSpool() {
        double spoolMotorPower = gamepad2.left_stick_y;
        spoolMotor.setPower(spoolMotorPower);
        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void relic() {
        if (gamepad2.dpad_up) {
            servo3.setPosition(-1);
        }
        if (gamepad2.dpad_down) {
            servo3.setPosition(1);
        }
        if (gamepad2.x) {
            servo2.setPosition(-1);
        }
        if (gamepad2.b) {
            servo2.setPosition(1);
        }
    }
}
