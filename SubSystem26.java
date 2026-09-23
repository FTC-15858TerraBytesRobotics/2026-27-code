package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class SubSystem26 {
    public Drivetrain26 drivetrain = new Drivetrain26();
    public intake26 intake =  new intake26();
    public Shooter26 shooter =  new Shooter26();
    public void  init (HardwareMap hardwareMap){
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
    }
}
