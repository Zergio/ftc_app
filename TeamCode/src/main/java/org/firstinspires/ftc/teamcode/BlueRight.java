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
 * Autonomous OpMode for the case where we are on the blue team on the right side of the field
 */
@Autonomous
public class BlueRight extends LinearOpMode {

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
        boolean isBlue = color0.blue() > color0.red();
        if (isBlue) {
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
        moveInch(31 + column,false);
        sleep(200);
        turn(-90);
        sleep(200);
        moveInch(6,false);
        sleep(200);
        pull(false);
        moveInch(2,false);
        moveInch(-3,false);
    }





    // Motors
    protected DcMotor motor0;
    protected DcMotor motor1;
    protected DcMotor spoolMotor;
    protected DcMotor rightClamp;
    protected DcMotor leftClamp;

    // Servos
    protected Servo colorServo;
    protected Servo colorServo2;
    protected Servo knockServo;

    // Color sensor
    protected LynxI2cColorRangeSensor color0;

    protected final String vuforiaLicense = "AXOZJVv/////AAAAGb2y8LIaVE3fi65/6/TwcCMDDJlAw0RjWXtTZPLR" +
            "Q2o5U7/aeLkF3wRej13hegHVoLuP3QCE5STcnpT0ajAMqa4ObqC3n6R6hWGUCPl5ZjtGeNLghtVE5pswlpc8/8" +
            "Z4GwJEE65mmAwo6tfS54FIpfVq7qLKF3rByohYrwwKZ1mQM6STF1t8IsbeXrBEtfCQN5fSX2wLMPSJE34Iz0Ig" +
            "VlSAVbJfdxKkX8JONhqeAOWseLUkG+fI+Da71V4eMzfHarfuN7Nltbd+3zNE7DIwFQs5/PDIotbVVpYrpS4wiH" +
            "1lNPNxWLSyv/ArSyCyNi9Ygi5W/UqIlQ+7Mweg6f16d6nZpMN1Ejv1o0s7L4L0aXny";

    // Declare OpMode members.
    protected ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    private VuforiaLocalizer vuforia;

    public double getColumn() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId",
                        "id",
                        hardwareMap.appContext.getPackageName());

        double outcome = 0;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = vuforiaLicense;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();

        this.resetStartTime();

        while (this.getRuntime() < 3.0 && opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                outcome = -7.5;
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                outcome = 7.5;
            } else {
                outcome = 0;
            };
            telemetry.update();
        }

        //For Debugging
        telemetry.addData("column", String.valueOf(outcome));
        telemetry.update();

        return outcome;
    };

    /*
     * Initialize the variables for the OpMode
     */
    protected void initOpMode() {
        // Motors initialization
        motor0 = hardwareMap.get(DcMotor.class, "motor0");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spoolMotor = hardwareMap.get(DcMotor.class, "spoolMotor");
        rightClamp = hardwareMap.get(DcMotor.class, "rightClamp");
        leftClamp = hardwareMap.get(DcMotor.class, "leftClamp");

        // Servos initialization
        colorServo = hardwareMap.get(Servo.class, "colorServo");
        colorServo2 = hardwareMap.get(Servo.class,"colorServo2");
        knockServo = hardwareMap.get(Servo.class,"knockServo");

        // Sensors intialization
        color0 = hardwareMap.get(LynxI2cColorRangeSensor.class, "color0");

        // initialize the spool encoder
        spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Lift the pull
     */
    protected void lift(double height) {
        // variable height is the position of the slide from 0
        double targetHeight = -600f * height;
        spoolMotor.setTargetPosition((int) targetHeight);
        spoolMotor.setPower(1);
        while (spoolMotor.isBusy()) ;
        spoolMotor.setPower(0);
        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Pull or push the glyph
     */
    protected void pull(boolean pull) {
        if (pull) {
            rightClamp.setPower(1);
            leftClamp.setPower(-1);
        } else {
            rightClamp.setPower(-1);
            leftClamp.setPower(1);
        }

//        if (doClose) {
//            servo0.setPosition(1);
//            servo1.setPosition(-1.5);
//        } else {
//            servo0.setPosition(0.25);
//            servo1.setPosition(0.5);
//        }
    }

    protected float getSaturation(int color) {
        float[] array = new float[3];
        Color.colorToHSV(color, array);
        return array[1];
    }

    protected float getHue(int color) {
        float[] array = new float[3];
        Color.colorToHSV(color, array);
        return array[0];
    }

    /**
     * Move the robot forward
     */
    protected void moveInch(double inches, boolean slow) {
        double power;

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
        motor0.setTargetPosition((int) (inches * -88));
        motor1.setTargetPosition((int) (inches * 88));

        // the maximum speed of the motors.
        if (slow) {
            power = .1;
        } else {
            power = .2;
        }
        motor0.setPower(power);
        motor1.setPower(power);
        // Loop until both motors are no longer busy.
        while (motor0.isBusy() || motor1.isBusy()) ;
        motor0.setPower(0);
        motor1.setPower(0);
    }

    protected void turn(double degrees) {
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Change 1440 appropriately if you are not using
        // Modern Robotics encoders
        motor0.setTargetPosition((int) (degrees * 12.8));
        motor1.setTargetPosition((int) (degrees * 12.8));
        motor0.setPower(0.35);
        motor1.setPower(0.35);
        while (motor0.isBusy() || motor1.isBusy()) ;
        motor0.setPower(0);
        motor1.setPower(0);
    }

    protected void raise (boolean lift) {
        if (lift) {
            colorServo.setPosition(-1);
            colorServo2.setPosition(1);
        } else {
            colorServo.setPosition(1);
            colorServo2.setPosition(-1);
        }
    }

    protected void knock (String direction) {
        if (direction == "nonsensor") {
            knockServo.setPosition(1);
            sleep(1000);
            knockServo.setPosition(.45);
        } else if (direction == "sensor") {
            knockServo.setPosition(-1);
            sleep(1000);
            knockServo.setPosition(.45);
        }
    }
}