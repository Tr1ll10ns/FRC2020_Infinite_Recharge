package frc.robot.subsystem.vision;

import frc.robot.config.Config;
import frc.robot.subsystem.BitBucketSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.utils.math.MathUtils;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionSubsystem extends BitBucketSubsystem {

    private double degreesToRotate = 0.0;
    private boolean validTarget = false;    

    private double defaultVal = 0;
    NetworkTable limelightTable;

    private double tx = 0;
    private double ty = 0;

    public VisionSubsystem(Config config) {
        super(config);
    }

    public void initialize() {
        super.initialize();

        NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
        tableInstance.startClientTeam(4183);

        limelightTable = tableInstance.getTable("limelight");
    }

	public void diagnosticsInitialize() {

    }
	
	public void diagnosticsPeriodic() {

    }
	
	public void diagnosticsCheck() {

    }

    @Override
    public void periodic(float deltaTime) {

        updateTargetInfo();
        double distance = approximateDistanceFromTarget(ty);

        SmartDashboard.putBoolean(getName() + "/Valid Target ", validTarget);
        SmartDashboard.putNumber(getName() + "/Estimated Distance ", distance);
    }

    public double getShooterVelocityForTarget() {
              
        double d = approximateDistanceFromTarget(ty);
        double h = VisionConstants.TARGET_HEIGHT_INCHES;
        double angle = VisionConstants.BALL_SHOOTING_ANGLE;

        double numerator = MathUtils.G * Math.pow(d, 2);
        double denominator = 2 * (d * Math.tan(angle) - h) * Math.pow(Math.cos(angle), 2);

        double vel = Math.sqrt(numerator / denominator);

        return vel;
    }

    public double approximateDistanceFromTarget(double ty) {
        return (VisionConstants.TARGET_HEIGHT_INCHES - VisionConstants.CAMERA_HEIGHT_INCHES) / Math.tan(VisionConstants.CAMERA_MOUNTING_ANGLE + ty);
    }

    public double queryLimelightNetworkTable(String value) {
        return limelightTable.getEntry(value).getDouble(defaultVal);
    }

	public void updateTargetInfo() {
        
        double tv = queryLimelightNetworkTable("tv");
        if (tv == 1) {
            validTarget = true;
        } else {
            validTarget = false;
        }

        tx = queryLimelightNetworkTable("tx");
        ty = queryLimelightNetworkTable("ty");
    }

    public double getDegreesToRotate() {
        return degreesToRotate;
    }

    public double getTx() {
        return tx;
    }

    public boolean getValidTarget() {
        return validTarget;
    }

}