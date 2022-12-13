// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.*;

public class Shooter extends SubsystemBase {
  
  //Creates shooter motor, pid controllers, and encoder objects
  private final CANSparkMax primaryFlywheel, secondaryFlywheel;
  private final SparkMaxPIDController primaryPidController, secondaryPidController;
  private final RelativeEncoder primaryEncoder, secondaryEncoder;

  // Current target speed
  private double primaryTargetSpeed, secondaryTargetSpeed;

  // Shuffleboard stuff for adjusting power during regression
  private final ShuffleboardTab regressionTab, smartDashboard;
  private final SimpleWidget /*shotPowerInput,*/ primarySpeedOutput, secondarySpeedOutput, primaryTargetOutput, secondaryTargetOutput, shotPowerAdjust;

  /** Creates a new Shooter. */
  public Shooter() {
    
    //Instantiates motors
    primaryFlywheel = new CANSparkMax(Constants.shooter_FLYWHEEL_PRIMARY_PORT, MotorType.kBrushless);
    secondaryFlywheel = new CANSparkMax(Constants.shooter_FLYWHEEL_SECONDARY_PORT, MotorType.kBrushless);

    primaryFlywheel.restoreFactoryDefaults();
    secondaryFlywheel.restoreFactoryDefaults();

    //Instantiates pid controllers
    primaryPidController = primaryFlywheel.getPIDController();
    secondaryPidController = secondaryFlywheel.getPIDController();

    //Sets pid controller P, I, and D values
    //primaryPidController.setP(.00000033728);
    primaryPidController.setP(.000032591);
    primaryPidController.setI(0.0000002);
    primaryPidController.setD(0);
    primaryPidController.setFF(0.0);

    secondaryPidController.setP(.000032591);
    secondaryPidController.setI(0.0000002);
    secondaryPidController.setD(0);
    secondaryPidController.setFF(0.0);

    //Instantiates motor encoders
    primaryEncoder = primaryFlywheel.getEncoder();
    secondaryEncoder = secondaryFlywheel.getEncoder();

    primaryEncoder.setVelocityConversionFactor(2);
    secondaryEncoder.setVelocityConversionFactor(2);

    primaryTargetSpeed = 0;
    secondaryTargetSpeed = 0;

    primaryFlywheel.setIdleMode(CANSparkMax.IdleMode.kCoast);
    secondaryFlywheel.setIdleMode(CANSparkMax.IdleMode.kCoast);

    // Shuffleboard tabs
    smartDashboard = Shuffleboard.getTab("SmartDashboard");
    regressionTab = Shuffleboard.getTab("Regression");
    //shotPowerInput = regressionTab.add("Shot Power", 0.0); 
    primarySpeedOutput = regressionTab.add("Primary Flywheel Speed", 0.0);
    secondarySpeedOutput = regressionTab.add("Secondary Flywheel Speed", 0.0);
    primaryTargetOutput = regressionTab.add("Primary Flywheel Target", 0.0);
    secondaryTargetOutput = regressionTab.add("Secondary Flywheel Target", 0.0);
    shotPowerAdjust = smartDashboard.add("Adjust Shot Power", 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Output the current flywheel speeds to Shuffleboard

    primarySpeedOutput.getEntry().setDouble(primaryEncoder.getVelocity());
    secondarySpeedOutput.getEntry().setDouble(secondaryEncoder.getVelocity());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void SetShooters(double primarySpeed, double secondarySpeed) { 

    //set motor speed with PID (experimental)
    primaryPidController.setReference(primarySpeed, CANSparkMax.ControlType.kVelocity);
    secondaryPidController.setReference(secondarySpeed, CANSparkMax.ControlType.kVelocity);
    
    //set motor speed with PWM (deprecated)
    //primaryFlywheel.set(primarySpeed);
    //secondaryFlywheel.set(secondarySpeed);

    primaryTargetSpeed = primarySpeed;
    secondaryTargetSpeed = secondarySpeed;

    primaryTargetOutput.getEntry().setDouble(primaryTargetSpeed);
    secondaryTargetOutput.getEntry().setDouble(secondaryTargetSpeed);

    // SmartDashboard.putNumber("Lower Shooter", primarySpeed);
    // SmartDashboard.putNumber("Upper Shooter", secondarySpeed);  
    
  }

  // Checks whether the flywheel velocities are within a set threshold of the desired velocity
  public boolean atTargetSpeed() {
    boolean primaryAtTarget = Math.abs(primaryEncoder.getVelocity() - primaryTargetSpeed) < Constants.auto_shooter_threshold;
    boolean secondaryAtTarget = Math.abs(secondaryEncoder.getVelocity() - secondaryTargetSpeed) < Constants.auto_shooter_threshold;
    
    return (primaryAtTarget && secondaryAtTarget);
  }

  // Shooter Math
  // https://www.desmos.com/calculator/jyxaoog9fd
  // Gets optimal angle of shot in degrees
  public double getShotAngle(double distance) {
    double x2 = distance;
    double y2 = Constants.hubHeight;
    double x1 = x2 + Constants.secondaryPointDistance;
    double y1 = y2 + Constants.secondaryPointHeight;

    //double A = ((x2*y1) - (x1 * y2))/((Math.pow(x1, 1) * x2)-(Math.pow(x2, 2) * x1));
    double B = ((-Math.pow(x2, 2) * y1)+(Math.pow(x1, 2) * y2))/((Math.pow(x1, 2) * x2)-(Math.pow(x2, 2) * x1));
    double shotAngle = Math.toDegrees(Math.atan(B));
    return shotAngle;
  }

  // Gets the distance to the goal given the angle read from the limelight
  public double getDistance(double angleToGoal) {
    double trueAngle = angleToGoal + Constants.limelightAngle;
    double height = Constants.hubHeight - Constants.limelightHeight; 
    double distance = height / Math.tan(Math.toRadians(trueAngle));
    distance += Constants.goalRadius;
    return distance;
  }

  // Waiting on Mathew for this method to actually do anything
  public double getSecondaryMotorSpeed(double primaryMotorSpeed, double angle) {
    return primaryMotorSpeed;
  }

  // Spins the motors at the velocity inputed on Shuffleboard
  public double getShotPower(double distance) {
    // https://www.desmos.com/calculator/vlunb8xtw2
    double power = (903.227 / (1 + Math.exp(-7.44717 * (distance - 3.36109)))) + 4254.94;
    power += shotPowerAdjust.getEntry().getDouble(0.0);
    return power;
  }

  public static boolean goodShotDistance(double angleToGoal) {
    double trueAngle = angleToGoal + Constants.limelightAngle;
    double height = Constants.hubHeight - Constants.limelightHeight; 
    double distance = height / Math.tan(Math.toRadians(trueAngle));
    distance += Constants.goalRadius;

    return (Constants.minimumShotDistance < distance) && (Constants.maximumShotDistance > distance);
  }
}
