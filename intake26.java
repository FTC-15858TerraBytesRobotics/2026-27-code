package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake26 {

    // Shooter + Intake motors
    private DcMotor intakeMotor;

    public void init(HardwareMap hardwareMap) {
        // Hardware mapping
        intakeMotor  = hardwareMap.get(DcMotor.class, "intake");

        // Motor directions
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

    }
    // Intake control
    private void adjustIntake(double intakePower) {
        intakeMotor.setPower(intakePower);
    }
}

