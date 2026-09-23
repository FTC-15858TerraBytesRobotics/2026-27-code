package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Mecanum Drive + Shooter + Servo", group = "LinearOpMode")
public class shooterServo extends LinearOpMode {

    // --- Drive motors ---
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // --- Shooter motor ---
    private DcMotor shooter;

    // --- Servo ---
    private Servo servo0;

    // --- Drive speed ---
    private double motorSpeed = 0.6; // default speed

    @Override
    public void runOpMode() {
        // Map hardware
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        servo0 = hardwareMap.get(Servo.class, "servo0");

        // Motor directions
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            adjustSpeed();
            driveMecanum();
            controlShooterAndServo();

            telemetry.addData("Drive Speed", motorSpeed);
            telemetry.update();
        }
    }

    // --- Adjust drive speed ---
    private void adjustSpeed() {
        if (gamepad1.x) motorSpeed = 0.3;
        else if (gamepad1.y) motorSpeed = 0.6;
        else if (gamepad1.b) motorSpeed = 0.9;
        else if (gamepad1.a) motorSpeed = 1.0;
    }

    // --- Mecanum drive logic ---
    private void driveMecanum() {
        double drive = -gamepad1.left_stick_y;   // forward/backward
        double strafe = gamepad1.left_stick_x;   // left/right
        double rotate = gamepad1.right_stick_x;  // rotation

        frontLeft.setPower((drive + strafe + rotate) * motorSpeed);
        frontRight.setPower((drive - strafe - rotate) * motorSpeed);
        backLeft.setPower((drive - strafe + rotate) * motorSpeed);
        backRight.setPower((drive + strafe - rotate) * motorSpeed);
    }

    // --- Shooter and Servo Control ---
    private void controlShooterAndServo() {
        // Left stick Y controls shooter motor
        double shooterPower = -gamepad1.left_stick_y;
        shooter.setPower(shooterPower);

        // Right stick Y controls servo position (0–1 range)
        double servoPos = (gamepad1.right_stick_y + 1) / 2.0;
        servo0.setPosition(servoPos);

        telemetry.addData("Shooter Power", shooterPower);
        telemetry.addData("Servo Position", servoPos);
    }
}
