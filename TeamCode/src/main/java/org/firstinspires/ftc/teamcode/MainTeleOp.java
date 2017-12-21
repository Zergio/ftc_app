package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ethan on 12/3/17.
 */
@TeleOp
public class MainTeleOp extends LinearOpMode {
    // Motors
    protected DcMotor motor0;
    protected DcMotor motor1;
    protected DcMotor spoolMotor;

    // Servos
    protected Servo servo0;
    protected Servo servo1;
    protected Servo servo2;
    protected Servo servo3;
    protected Servo servo5;

    // Color sensor
    protected LynxI2cColorRangeSensor color0;

    /**
     * Initialize the variables for the OpMode
     */
    protected void initOpMode() {
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
        spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    int slow = 2;

    public void runOpMode() {
        initOpMode();
        waitForStart();

        while (opModeIsActive()) {
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
            motor0.setPower(gamepad1.right_stick_x / slow * 2);
            motor1.setPower(gamepad1.right_stick_x / slow * 2);
        }
    }

    private void runToggleClamp() {
        if (gamepad2.left_bumper) {
            servo0.setPosition(0);
            servo1.setPosition(0.7);
        }
        if (gamepad2.right_bumper) {
            servo0.setPosition(1);
            servo1.setPosition(-1.5);
        }
        if (gamepad2.a) {
            servo0.setPosition(0.37);
            servo1.setPosition(0.3);
        }
    }
;
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
