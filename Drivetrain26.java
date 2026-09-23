package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain26 {
    // Drive motors
    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;

    public void init(HardwareMap hardwareMap) {
        // Hardware mapping
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        leftBackDrive   = hardwareMap.get(DcMotor.class, "backLeft");
        rightBackDrive  = hardwareMap.get(DcMotor.class, "backRight");


        // Motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

    }

    // Mecanum drive math
    private void driveMecanum(double motorSpeed, double drive, double strafe, double rotate) {
        leftFrontDrive.setPower((drive + strafe + rotate) * motorSpeed);
        rightFrontDrive.setPower((drive - strafe - rotate) * motorSpeed);
        leftBackDrive.setPower((drive - strafe + rotate) * motorSpeed);
        rightBackDrive.setPower((drive + strafe - rotate) * motorSpeed);
    }
}