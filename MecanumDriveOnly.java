package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mecanum Drive Only", group = "LinearOpMode")
public class MecanumDriveOnly extends LinearOpMode {


    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;

    // Drive speed
    private double motorSpeed = 0.6; // default speed

    @Override
    public void runOpMode() {
        // Map hardware
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backLeft");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRight");

        // Set motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            adjustSpeed();
            driveMecanum();
            telemetry.addData("Drive Speed", motorSpeed);
            telemetry.update();
        }
    }

    // Adjust speed using gamepad buttons
    private void adjustSpeed() {
        if (gamepad1.x) motorSpeed = 0.3;
        else if (gamepad1.y) motorSpeed = 0.6;
        else if (gamepad1.b) motorSpeed = 0.9;
        else if (gamepad1.a) motorSpeed = 1.0;
    }

    // Mecanum drive calculations
    private void driveMecanum() {
        double drive = -gamepad1.left_stick_y;   // forward/backward
        double strafe = gamepad1.left_stick_x;  // left/right
        double rotate = gamepad1.right_stick_x; // rotation

        leftFrontDrive.setPower((drive + strafe + rotate) * motorSpeed);
        rightFrontDrive.setPower((drive - strafe - rotate) * motorSpeed);
        leftBackDrive.setPower((drive - strafe + rotate) * motorSpeed);
        rightBackDrive.setPower((drive + strafe - rotate) * motorSpeed);
    }
}
