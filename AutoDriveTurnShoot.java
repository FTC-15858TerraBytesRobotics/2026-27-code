package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto Drive Forward", group = "Auto")
public class AutoDriveTurnShoot extends LinearOpMode {

    private DcMotor shooter;

    // Drive motors
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // Alliance selection
    private String allianceColor = "none"; // "red" or "blue"

    @Override
    public void runOpMode() {

        // --- Initialize hardware ---
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        telemetry.addLine("Use D-pad LEFT for BLUE, RIGHT for RED");
        telemetry.update();

        // --- Select alliance before start ---
        while (!opModeIsActive() && !isStopRequested()) {
            if (gamepad1.dpad_left) {
                allianceColor = "blue";
            } else if (gamepad1.dpad_right) {
                allianceColor = "red";
            }
            telemetry.addData("Selected Alliance", allianceColor.equals("none") ? "None" : allianceColor.toUpperCase());
            telemetry.update();
        }

        waitForStart();

        if (opModeIsActive()) {

            // --- Step 1: Drive forward ---
            frontLeft.setPower(-0.6);
            frontRight.setPower(0.6);
            backLeft.setPower(-0.6);
            backRight.setPower(0.6);
            sleep(2000); // drive forward 2 sec
            if (allianceColor.equals("blue")) {
                // Turn right
                frontLeft.setPower(-0.2);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(-0.2);
            } else {
                // Turn left
                frontLeft.setPower(0);
                frontRight.setPower (-0.2);
                backLeft.setPower(-0.2);
                backRight.setPower(0);
            }
            sleep(200); // ~360° turn
            frontLeft.setPower(0.4);
            frontRight.setPower(0.4);
            backLeft.setPower(0.4);
            backRight.setPower(0.4);
            sleep(200);
            telemetry.addData("Auto Complete", "Done for " + allianceColor.toUpperCase());
            telemetry.update();
        }
    }
}
