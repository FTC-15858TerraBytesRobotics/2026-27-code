package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Single Shooter Motor Adjustable (6000RPM)", group = "LinearOpMode")
public class SingleShooterMotorAdjustable extends LinearOpMode {

    private DcMotor shooterMotor;

    @Override
    public void runOpMode() {
        // ⚠️ Update this name to match your config!
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");

        telemetry.addLine("Shooter Ready - Controller 2");
        telemetry.addLine("Y = Adjust with left stick");
        telemetry.addLine("A = Save current speed");
        telemetry.addLine("B = Full speed | X = Slow speed");
        telemetry.addLine("LT = Emergency Stop");
        telemetry.update();

        waitForStart();

        double power = 0;
        double savedPower = 0;
        boolean adjusting = false;

        while (opModeIsActive()) {

            // --- Adjust mode with Y ---
            if (gamepad2.y) {
                adjusting = true;
            }

            // --- Save current speed with A ---
            if (gamepad2.a) {
                savedPower = power;
                adjusting = false; // exit adjust mode
            }

            // --- Preset speed buttons ---
            if (gamepad2.b) {
                power = 1.0;  // full speed
                adjusting = false;
            } else if (gamepad2.x) {
                power = 0.5;  // slow speed
                adjusting = false;
            }

            // --- Adjust power with left stick when in adjust mode ---
            if (adjusting) {
                power = -gamepad2.left_stick_y; // up = +1, down = -1
            } else {
                power = savedPower != 0 ? savedPower : power;
            }

            // --- Emergency stop ---
            if (gamepad2.left_trigger > 0.5) {
                power = 0.0;
                savedPower = 0.0;
                adjusting = false;
            }

            shooterMotor.setPower(power);

            telemetry.addData("Shooter Power", "%.2f", power);
            telemetry.addData("Saved Power", "%.2f", savedPower);
            telemetry.addData("Adjust Mode", adjusting ? "ON" : "OFF");
            telemetry.update();
        }
    }
}
