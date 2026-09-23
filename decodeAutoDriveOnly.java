package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.BNO055IMU;

@Autonomous(name = "Auto Drive Only (Adjustable)", group = "LinearOpMode")
public class decodeAutoDriveOnly extends LinearOpMode {

    // ---------------------
    // Hardware
    // ---------------------
    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;
    private BNO055IMU imu;

    // ---------------------
    // Adjustable auto timing (change to tune distance/turn)
    // ---------------------
    private long DRIVE_TO_GOAL_TIME = 2300;        // how long to drive forward (ms)
    private long TURN_TIME = 220;                  // how long to turn (ms)
    private long PLAYER_STATION_DRIVE_TIME = 2800; // drive to player station
    private long WAIT_AT_PLAYER_STATION = 5000;    // wait before driving back (ms)

    enum Alliance { RED, BLUE }
    private Alliance alliance = Alliance.RED; // default

    @Override
    public void runOpMode() {
        // ---------------------
        // Hardware map
        // ---------------------
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontleft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontright");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backLeft");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRight");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        // Motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        // Choose alliance before start
        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.dpad_left) alliance = Alliance.RED;
            if (gamepad1.dpad_right) alliance = Alliance.BLUE;

            telemetry.addData("Alliance", alliance);
            telemetry.addLine("Press D-Pad Left = RED, Right = BLUE");
            telemetry.addLine("Adjust drive/turn times in code for distance tuning");
            telemetry.update();
        }

        waitForStart();

        if (alliance == Alliance.RED) {
            autoDriveRed();
        } else {
            autoDriveBlue();
        }

        stopAllMotors();
    }

    // ==========================
    // Auto routines
    // ==========================
    private void autoDriveRed() {
        telemetry.addLine("Running RED Auto...");
        telemetry.update();

        driveToGoalRed();
        driveToPlayerStationAndBackRed();
    }

    private void autoDriveBlue() {
        telemetry.addLine("Running BLUE Auto...");
        telemetry.update();

        driveToGoalBlue();
        driveToPlayerStationAndBackBlue();
    }

    // ==========================
    // Movement sequences
    // ==========================
    private void driveToGoalRed() {
        driveForward(1, DRIVE_TO_GOAL_TIME);
        turn(-1, TURN_TIME); // left turn for RED
        sleep(500);
    }

    private void driveToGoalBlue() {
        driveForward(1, DRIVE_TO_GOAL_TIME);
        turn(1, TURN_TIME); // right turn for BLUE
        sleep(500);
    }

    private void driveToPlayerStationAndBackRed() {
        driveForward(-1, PLAYER_STATION_DRIVE_TIME);
        sleep(WAIT_AT_PLAYER_STATION);
        driveForward(1, PLAYER_STATION_DRIVE_TIME);
        sleep(500);
    }

    private void driveToPlayerStationAndBackBlue() {
        driveForward(-1, PLAYER_STATION_DRIVE_TIME);
        sleep(WAIT_AT_PLAYER_STATION);
        driveForward(1, PLAYER_STATION_DRIVE_TIME);
        sleep(500);
    }

    // ==========================
    // Drive helpers
    // ==========================
    private void driveForward(double power, long duration) {
        setMotorPower(power, power, power, power);
        sleep(duration);
        stopAllMotors();
    }

    private void turn(double direction, long duration) {
        // direction = +1 (right), -1 (left)
        setMotorPower(direction, -direction, direction, -direction);
        sleep(duration);
        stopAllMotors();
    }

    private void setMotorPower(double lf, double rf, double lb, double rb) {
        leftFrontDrive.setPower(lf);
        rightFrontDrive.setPower(rf);
        leftBackDrive.setPower(lb);
        rightBackDrive.setPower(rb);
    }

    private void stopAllMotors() {
        setMotorPower(0, 0, 0, 0);
    }
}
