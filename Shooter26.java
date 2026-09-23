package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter26 {


    // Shooter motors
    private DcMotor shooterMotor;

    public void init(HardwareMap hardwareMap){
        // Hardware mapping
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        shooterMotor.setDirection(DcMotor.Direction.REVERSE);

    }

    // Shooter control (67% manual response)
    private void adjustShooter(double shooterPower) {
        shooterMotor.setPower(shooterPower);
    }
}
