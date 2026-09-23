package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Right Joystick Servo Control", group = "Examples")
public class RightJoystickServoControl extends LinearOpMode {

    private Servo myServo;

    // Servo limits (degrees)
    private static final double MIN_DEG = 45.0;   // lowest servo angle
    private static final double MAX_DEG = 135.0;  // highest servo angle
    private double currentAngle = 90.0;           // start centered at 90°

    @Override
    public void runOpMode() {
        myServo = hardwareMap.get(Servo.class, "myServo");

        // Initialize servo at 90° (center)
        myServo.setPosition(currentAngle / 180.0);

        waitForStart();

        while (opModeIsActive()) {
            // 🎮 Right joystick Y (up = -1, down = +1)
            double joyY = -gamepad2.right_stick_y;

            // Clamp joystick range
            joyY = Math.max(-1.0, Math.min(1.0, joyY));

            // Adjust angle based on joystick movement
            // Move faster with more stick deflection
            currentAngle += joyY * 2.0; // 2° per cycle; adjust for sensitivity

            // Clamp to servo limits
            currentAngle = Math.max(MIN_DEG, Math.min(MAX_DEG, currentAngle));

            // Update servo position
            myServo.setPosition(currentAngle / 180.0);

            // Telemetry
            telemetry.addData("Right Stick Y", joyY);
            telemetry.addData("Servo Angle", currentAngle);
            telemetry.addData("Servo Position", currentAngle / 180.0);
            telemetry.update();
        }
    }
}
