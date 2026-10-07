package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "SubSystemsTeleOP26", group = "LinearOpMode")
public class TeleOP26 extends LinearOpMode {

    public SubSystem26 subsystem26 =  new SubSystem26();
    private double motorSpeed = 1;

    // Shooter variables

    private double shooterPower = 0.0;
    private double savedShooterPower = 0.0;
    private boolean manualShooterMode = false;

    // Intake variables
    private double intakePower = 0.0;
    private double savedIntakePower = 0.0;

    public void runOpMode() {
        //----------------------------------------
        //TELEOP
        //----------------------------------------

        // Recall from Subsystems
        subsystem26.init(hardwareMap);

        telemetry.addLine("Ready!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            driveMecanum();
            adjustDriveSpeed();
            adjustIntake();
            adjustShooter();

            telemetry.addData("Drive Speed", motorSpeed);

            telemetry.addLine("---- Intake ----");
            telemetry.addData("Intake Power", intakePower);
            telemetry.addData("Saved Intake", savedIntakePower);

            telemetry.addLine("---- Shooter ----");
            telemetry.addData("Shooter Power", shooterPower);
            telemetry.addData("Saved Shooter", savedShooterPower);
            telemetry.addData("Manual Mode", manualShooterMode);
            telemetry.update();
        }
    }
    private void driveMecanum() {
        double drive = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;
        subsystem26.drivetrain.driveMecanum(motorSpeed,drive,strafe,rotate);
    }

    // Drive speed control
    private void adjustDriveSpeed() {
        if (gamepad1.x) motorSpeed = 0.3;
        else if (gamepad1.y) motorSpeed = 0.6;
        else if (gamepad1.b) motorSpeed = 0.9;
        else if (gamepad1.a) motorSpeed = 1.0;
    }

    //----------------------------------------
    //INTAKE
    //----------------------------------------
    // Intake control
    private void adjustIntake() {

        double live = -gamepad2.left_stick_y;
        subsystem26.intake.adjustIntake(intakePower);

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

        subsystem26.shooter.adjustShooter(shooterPower);

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
        if (manualShooterMode) {

            boolean manualAllowed = Math.random() < 0.67;  // 67% reliability

            if (manualAllowed) {
                shooterPower = -gamepad2.right_stick_y;
                shooterPower = Math.max(0, Math.min(1, shooterPower));
            }

            // Manual shooter control (67% chance to respond)
            if (manualShooterMode) {

                if (manualAllowed) {
                    shooterPower = -gamepad2.right_stick_y;
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
            // When not in manual mode, use saved shooter power
            if (!manualShooterMode) {
                shooterPower = savedShooterPower;
            }
        }
    }
}
