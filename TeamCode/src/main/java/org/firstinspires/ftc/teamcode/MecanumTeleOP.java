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
public class MecanumTeleOP extends LinearOpMode {
    // Motors
    protected DcMotor motor0;
    protected DcMotor motor1;
    protected DcMotor spoolMotor;
    protected DcMotor spoolMotor2;
    protected DcMotor rightClamp;
    protected DcMotor leftClamp;

    // Servos
    protected Servo servo0;
    protected Servo servo1;
    protected Servo servo2;
    protected Servo colorServo;
    protected Servo colorServo2;

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
        spoolMotor2 = hardwareMap.get(DcMotor.class,"spoolMotor2");
        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spoolMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightClamp = hardwareMap.get(DcMotor.class, "rightClamp");
        leftClamp = hardwareMap.get(DcMotor.class, "leftClamp");

        // Servos initialization
        servo0 = hardwareMap.get(Servo.class, "servo0");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        colorServo = hardwareMap.get(Servo.class, "colorServo");
        colorServo2 = hardwareMap.get(Servo.class,"colorServo2");

        // Sensors initialization
        color0 = hardwareMap.get(LynxI2cColorRangeSensor.class, "color0");

        // initialize the spool encoder
        spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    double slow = 2;

    public void runOpMode() {
        initOpMode();
        waitForStart();

        while (opModeIsActive()) {
            drive();
            runClamp();
            runSpool();
//            relic();
            if (gamepad2.a) {
                colorServo.setPosition(-0.65);
                colorServo2.setPosition(.65);
            }
        }
    }

    private void drive() {
        if (gamepad1.right_bumper) {
            slow = 1;
        }
        if (gamepad1.left_bumper) {
            slow = 1.66;
        }

        motor0.setPower((gamepad1.left_stick_y / slow) + (gamepad1.right_stick_x / slow));
        motor1.setPower((-1 * gamepad1.left_stick_y / slow) + (gamepad1.right_stick_x / slow));
    }

    private void runClamp() {
        // Mecanum Clamp

        // Normal ejecting and pulling
        rightClamp.setPower(gamepad2.right_trigger);
        leftClamp.setPower(-gamepad2.right_trigger);
        rightClamp.setPower(-gamepad2.left_trigger);
        leftClamp.setPower(gamepad2.left_trigger);

        // Glyph Turning
        if (gamepad2.left_bumper) {
            rightClamp.setPower(1);
            leftClamp.setPower(1);
        } else if (gamepad2.right_bumper) {
            rightClamp.setPower(-1);
            leftClamp.setPower(-1);
        }

        // Old Clamp
//        if (gamepad2.left_bumper) {
//            servo0.setPosition(0);
//            servo1.setPosition(0.7);
//        }
//        if (gamepad2.right_bumper) {
//            servo0.setPosition(1);
//            servo1.setPosition(-1.5);
//        }
//        if (gamepad2.a) {
//            servo0.setPosition(0.37);
//            servo1.setPosition(0.3);
//        }
    }
    ;
    private void runSpool() {
        spoolMotor.setPower(-gamepad2.left_stick_y);
        spoolMotor2.setPower(-gamepad2.left_stick_y);
    }

//    private void relic() {
//        if (gamepad2.dpad_up) {
//            servo3.setPosition(-1);
//        }
//        if (gamepad2.dpad_down) {
//            servo3.setPosition(1);
//        }
//        if (gamepad2.x) {
//            servo2.setPosition(-1);
//        }
//        if (gamepad2.b) {
//            servo2.setPosition(1);
//        }
//    }
}
