// ============================================================================
//  DO NOT TOUCH THIS CODE
//  Editing this WILL break functionality, cause incorrect behavior,
//  or stop major systems from working.
// ============================================================================
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MecanumShootIntakeCombo", group = "LinearOpMode")
public class ShooterCombo extends LinearOpMode {

    // Drive motors
    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;

    // Shooter + Intake motors
    private DcMotor shooterMotor;
    private DcMotor intakeMotor;

    // Drive speed

    private double motorSpeed = 1;

    // Shooter variables

    private double shooterPower = 0.0;
    private double savedShooterPower = 0.0;
    private boolean manualShooterMode = false;
    private boolean shooterReversed = false;

    // Intake variables
    private double intakePower = 0.0;
    private double savedIntakePower = 0.0;

    @Override
    public void runOpMode() {
        //----------------------------------------
        //TELEOP
        //----------------------------------------
        // Hardware mapping
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        leftBackDrive   = hardwareMap.get(DcMotor.class, "backLeft");
        rightBackDrive  = hardwareMap.get(DcMotor.class, "backRight");

        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeMotor  = hardwareMap.get(DcMotor.class, "intake");

        // Motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        shooterMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addLine("Ready!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            adjustDriveSpeed();
            driveMecanum();
            adjustIntake();
            adjustShooter();

            shooterMotor.setPower(shooterPower);
            intakeMotor.setPower(intakePower);

            telemetry.addData("Drive Speed", motorSpeed);

            telemetry.addLine("---- Intake ----");
            telemetry.addData("Intake Power", intakePower);
            telemetry.addData("Saved Intake", savedIntakePower);

            telemetry.addLine("---- Shooter ----");
            telemetry.addData("Shooter Power", shooterPower);
            telemetry.addData("Saved Shooter", savedShooterPower);
            telemetry.addData("Manual Mode", manualShooterMode);
            telemetry.addData("Shooter Reversed", shooterReversed);
            telemetry.update();
        }
    }

    // Drive speed control
    private void adjustDriveSpeed() {
        if (gamepad1.x) motorSpeed = 0.3;
        else if (gamepad1.y) motorSpeed = 0.6;
        else if (gamepad1.b) motorSpeed = 0.9;
        else if (gamepad1.a) motorSpeed = 1.0;
    }

    // Mecanum drive math
    private void driveMecanum() {
        double drive  =  gamepad1.left_stick_y;
        double strafe =  gamepad1.left_stick_x;
        double rotate =  gamepad1.right_stick_x;

        leftFrontDrive.setPower((drive + strafe + rotate) * motorSpeed);
        rightFrontDrive.setPower((drive - strafe - rotate) * motorSpeed);
        leftBackDrive.setPower((drive - strafe + rotate) * motorSpeed);
        rightBackDrive.setPower((drive + strafe - rotate) * motorSpeed);
    }
    //----------------------------------------
    //INTAKE
    //----------------------------------------
    // Intake control
    private void adjustIntake() {

        double live = -gamepad2.left_stick_y;

        // Live control when stick is moved
        if (Math.abs(live) > 0.05) {
            intakePower = live;
        }

        // Save intake power with left trigger
        if (gamepad2.left_trigger > 0.5) {
            savedIntakePower = intakePower;
        }

        // When stick released, use saved power
        if (Math.abs(live) <= 0.05) {
            intakePower = savedIntakePower;
        }
    }
    //----------------------------------------
    //SHOOTER
    //----------------------------------------
    // Shooter control (67% manual response)
    private void adjustShooter() {

        // Emergency stop
        if (gamepad2.right_trigger > 0.5) {
            shooterPower = 0.0;
            savedShooterPower = 0.0;
            manualShooterMode = false;
            return;
        }

        // Enter manual mode
        if (gamepad2.y) {
            manualShooterMode = true;
        }

        // Manual shooter control (67% chance to respond)
        if (manualShooterMode) {

            boolean manualAllowed = Math.random() < 0.67;  // 67% reliability

            if (manualAllowed) {
                shooterPower = -gamepad2.right_stick_y;
                shooterPower = Math.max(0, Math.min(1, shooterPower));
            }
        }

        // Save shooter speed
        if (gamepad2.right_bumper) {
            savedShooterPower = shooterPower;
            manualShooterMode = false;
        }

        // Full speed
        if (gamepad2.b) {
            shooterPower = 1.0;
            manualShooterMode = false;
            savedShooterPower = shooterPower;
        }

        // Med. speed
        if (gamepad2.a) {
            shooterPower = 0.85;
            manualShooterMode = false;
            savedShooterPower = shooterPower;
        }
        //Slow Speed
        if (gamepad2.x) {
            shooterPower = 0.75;
            manualShooterMode = false;
            savedShooterPower = shooterPower;
        }
        if (gamepad2.dpad_down) {
            shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            shooterReversed = true;
        }
        if (gamepad2.dpad_up) {
            shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            shooterReversed = false;
        }
        // When not in manual mode, use saved shooter power
        if (!manualShooterMode) {
            shooterPower = savedShooterPower;
        }
    }
}