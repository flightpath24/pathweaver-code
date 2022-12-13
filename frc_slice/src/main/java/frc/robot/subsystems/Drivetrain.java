/// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.*;
import frc.robot.auto.Paths;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.*;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.util.Units;

import com.kauailabs.navx.frc.AHRS;

public class Drivetrain extends SubsystemBase {
  
  //Creates drivetrain motor objects and groups
  private final CANSparkMax leftMotorFront, leftMotorBack, rightMotorFront, rightMotorBack;
  private final MotorControllerGroup leftMotors, rightMotors;
  private final DifferentialDrive robotDrive;

  private final AHRS navXGyro;

  //private final DifferentialDriveOdometry m_drivetrainOdometry;

  public final RelativeEncoder leftEncoderFront, leftEncoderBack, rightEncoderFront, rightEncoderBack;
  
  // PID Controllers
  public final SparkMaxPIDController leftPIDFront, leftPIDBack, rightPIDFront, rightPIDBack;

  public static DifferentialDrivePoseEstimator m_poseEstimator;

  public static final Field2d field2d = new Field2d();

  public double leftFrontLastPosition, leftBackLastPosition, rightFrontLastPosition, rightBackLastPosition;

  // The current target position of every motor
  public double leftTargetPositionFront, leftTargetPositionBack, rightTargetPositionFront, rightTargetPositionBack;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    
    //Instantiates motors and motor groups
    leftMotorFront = new CANSparkMax(Constants.drivetrain_LEFT_FRONT_PORT, MotorType.kBrushless);
    leftMotorBack = new CANSparkMax(Constants.drivetrain_LEFT_BACK_PORT, MotorType.kBrushless);
    rightMotorFront = new CANSparkMax(Constants.drivetrain_RIGHT_FRONT_PORT, MotorType.kBrushless);
    rightMotorBack = new CANSparkMax(Constants.drivetrain_RIGHT_BACK_PORT, MotorType.kBrushless);

    //rightMotorBack.setInverted(true);
    //rightMotorFront.setInverted(true);

    leftMotors = new MotorControllerGroup(leftMotorFront, leftMotorBack);
    rightMotors = new MotorControllerGroup(rightMotorFront, rightMotorBack);

    rightMotors.setInverted(true);

    leftMotorFront.restoreFactoryDefaults();
    leftMotorBack.restoreFactoryDefaults();
    rightMotorFront.restoreFactoryDefaults();
    rightMotorBack.restoreFactoryDefaults();

    leftMotorFront.setIdleMode(CANSparkMax.IdleMode.kBrake);
    leftMotorBack.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightMotorFront.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightMotorBack.setIdleMode(CANSparkMax.IdleMode.kBrake);

    robotDrive = new DifferentialDrive(leftMotors, rightMotors);

    leftEncoderFront = leftMotorFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.drivetrain_ENCODER_CPR);
    leftEncoderBack = leftMotorBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.drivetrain_ENCODER_CPR);

    rightEncoderFront = rightMotorFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.drivetrain_ENCODER_CPR);
    rightEncoderBack = rightMotorBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.drivetrain_ENCODER_CPR);

    leftEncoderFront.setVelocityConversionFactor(Constants.drivetrain_VELOCITY_CONVERSION_FACTOR);
    leftEncoderBack.setVelocityConversionFactor(Constants.drivetrain_VELOCITY_CONVERSION_FACTOR);

    rightEncoderFront.setVelocityConversionFactor(Constants.drivetrain_VELOCITY_CONVERSION_FACTOR);
    rightEncoderBack.setVelocityConversionFactor(Constants.drivetrain_VELOCITY_CONVERSION_FACTOR);

    leftEncoderFront.setPositionConversionFactor(Constants.drivetrain_POSITION_CONVERSION_RATIO);
    leftEncoderBack.setPositionConversionFactor(Constants.drivetrain_POSITION_CONVERSION_RATIO);

    rightEncoderFront.setPositionConversionFactor(Constants.drivetrain_POSITION_CONVERSION_RATIO);
    rightEncoderBack.setPositionConversionFactor(Constants.drivetrain_POSITION_CONVERSION_RATIO);

    leftPIDFront = leftMotorFront.getPIDController();
    leftPIDBack = leftMotorBack.getPIDController();
    rightPIDFront = rightMotorFront.getPIDController();
    rightPIDBack = rightMotorBack.getPIDController();

    navXGyro = new AHRS(SerialPort.Port.kUSB1);

    //m_drivetrainOdometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());

    //These standard deviation values should be measured proplerly for our robot
    m_poseEstimator = new DifferentialDrivePoseEstimator(new Rotation2d(Units.degreesToRadians(getHeading())),
      new Pose2d(),
      new MatBuilder<>(Nat.N5(), Nat.N1()).fill(0.02, 0.02, 0.01, 0.02, 0.02), // State measurement standard deviations. X, Y, theta.
      new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01), // Local measurement standard deviations. Left encoder, right encoder, gyro.
      new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.1, 0.1, 0.01)); // Global measurement standard deviations. X, Y, and theta.*/

    // Display current gyro heading on Shuffleboard
    Shuffleboard.getTab("SmartDashboard").add(navXGyro);
    // Display how the robot is moving on Shuffleboard
    Shuffleboard.getTab("SmartDashboard").add(robotDrive);
    
  }

  @Override
  public void periodic() {
    
    updateOdometry();

    // This method will be called once per scheduler run
    field2d.setRobotPose(getEstimatedPosition());

    SmartDashboard.putNumber("Left Side Position: ", getAverageLeftEncoderDistance());
    SmartDashboard.putNumber("Right Side Position: ", getAverageRightEncoderDistance());

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public static void updateField2d(int trajectoryNumber) {

    // Creates and pushes Field2d to SmartDashboard.
    SmartDashboard.putData(field2d);

    // Pushes the trajectory to Field2d.
    try {
      field2d.getObject("Trajectory").setTrajectory(Paths.getAutoPath().get(trajectoryNumber - 1));
    } catch (Exception exception) {
      field2d.getObject("Trajectory").setTrajectory(Paths.returnPlaceholderTrajectory());
    }

  }

  public Pose2d getEstimatedGlobalPose(Pose2d estimatedRobotPose) {

    //These white noise element intensity values should be proplery measured for our robot
    var rand = 
      StateSpaceUtil.makeWhiteNoiseVector(VecBuilder.fill(0, 0, Units.degreesToRadians(0)));

    return new Pose2d(
        estimatedRobotPose.getX() + rand.get(0, 0),
        estimatedRobotPose.getY() + rand.get(1, 0),
        estimatedRobotPose.getRotation().plus(new Rotation2d(rand.get(2, 0))));

  }

  public Pose2d updateOdometry() {

    m_poseEstimator.update(
      new Rotation2d(Units.degreesToRadians(getHeading())), 
        new DifferentialDriveWheelSpeeds(
          leftEncoderFront.getVelocity() * 2, 
          -rightEncoderFront.getVelocity() * 2),
          ((leftEncoderFront.getPosition() + leftEncoderBack.getPosition()) - (leftFrontLastPosition + leftBackLastPosition)),
          -((rightEncoderFront.getPosition() + rightEncoderBack.getPosition()) - (rightFrontLastPosition + rightBackLastPosition)));

          leftFrontLastPosition = leftEncoderFront.getPosition();
          leftBackLastPosition = leftEncoderBack.getPosition();
          rightFrontLastPosition = rightEncoderFront.getPosition();
          rightBackLastPosition = rightEncoderBack.getPosition();


    //This latency value(0.3) is a place holder for now and should be measured properly for our robot
    // m_poseEstimator.addVisionMeasurement(
    //   getEstimatedGlobalPose(m_poseEstimator.getEstimatedPosition()),
    //   Timer.getFPGATimestamp() - 0.3);

    return m_poseEstimator.getEstimatedPosition();

  }

  public Pose2d getEstimatedPosition() {
    return m_poseEstimator.getEstimatedPosition();
  }

  /**
   * Drives the robot at given x, y and theta speeds. Speeds range from [-1, 1] and the linear
   * speeds have no effect on the angular speed.
   *
   * @param forwardSpeed Speed of the robot moving in the x and y directions direction (left/right/forwards/bacwards).
   * @param turnSpeed Speed of the robot rotating.
   * @param fieldRelative Whether the provided move and rotate speeds are relative to the field.
   */

  public void ArcadeDrive(double forwardSpeed, double turnSpeed) { 

    robotDrive.arcadeDrive(-forwardSpeed, turnSpeed);

  }

  public void PIDArcadeDrive(double forwardSpeed, double turnSpeed) {
    WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(-forwardSpeed, turnSpeed, true);

    leftPIDFront.setReference(speeds.left, ControlType.kVelocity);
    leftPIDBack.setReference(speeds.left, ControlType.kVelocity);
    rightPIDFront.setReference(-speeds.right, ControlType.kVelocity);
    rightPIDBack.setReference(-speeds.right, ControlType.kVelocity);

    robotDrive.feed();
  }

  public void resetOdometry(Pose2d position) {

    leftEncoderFront.setPosition(0);
    leftEncoderBack.setPosition(0);
    rightEncoderFront.setPosition(0);
    rightEncoderBack.setPosition(0);

    m_poseEstimator.resetPosition(position, new Rotation2d(Units.degreesToRadians(getHeading())));

    //navXGyro.reset();
    //navXGyro.zeroYaw();

  }

  public double getLeftFrontVelocity() {

    return
    leftEncoderFront.getVelocity();

  }

  public double getLeftBackVelocity() {

    return
    leftEncoderBack.getVelocity();

  }

  public double getRightFrontVelocity() {

    return
    rightEncoderFront.getVelocity();

  }

  public double getRightBackVelocity() {

    return
    rightEncoderBack.getVelocity();

  }

  public double getAverageEncoderDistance() {
    return (getAverageLeftEncoderDistance() + getAverageRightEncoderDistance()) / 2.0;
  }

  public double getAverageLeftEncoderDistance() {
    return (leftEncoderFront.getPosition() + leftEncoderBack.getPosition()) / 2.0; 
  }

  public double getAverageRightEncoderDistance() {
    return -(rightEncoderFront.getPosition() + rightEncoderBack.getPosition()) / 2.0; 
  }

  public double getAverageLeftEncoderVelocity() {
    return (leftEncoderFront.getVelocity() + leftEncoderBack.getVelocity()) / 2.0; 
  }

  public double getAverageRightEncoderVelocity() {
    return (rightEncoderFront.getVelocity() + rightEncoderBack.getVelocity()) / 2.0; 
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoderFront.getVelocity(), -rightEncoderFront.getVelocity());
  }

  
  public double getHeading() {

    return -navXGyro.getYaw() + 180;

  }

  public double getTurnRate() {

    return navXGyro.getRate();

  }

  public void setPIDF(double kP, double kI, double kD, double kF) {
    leftPIDFront.setP(kP);
    leftPIDBack.setP(kP);
    rightPIDFront.setP(kP);
    rightPIDBack.setP(kP);

    leftPIDFront.setI(kI);
    leftPIDBack.setI(kI);
    rightPIDFront.setI(kI);
    rightPIDBack.setI(kI);

    leftPIDFront.setD(kD);
    leftPIDBack.setD(kD);
    rightPIDFront.setD(kD);
    rightPIDBack.setD(kD);

    leftPIDFront.setFF(kF);
    leftPIDBack.setFF(kF);
    rightPIDFront.setFF(kF);
    rightPIDBack.setFF(kF);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(rightVolts);
    robotDrive.feed();
  }

  /**
   * Drives forwards a given distance
   * @param distance in meters
   */
  public void driveDistance(double distance) {

    leftTargetPositionFront = leftEncoderFront.getPosition() + distance;
    leftTargetPositionBack = leftEncoderBack.getPosition() + distance;
    rightTargetPositionFront = rightEncoderFront.getPosition() - distance;
    rightTargetPositionBack = rightEncoderBack.getPosition() - distance;

    leftPIDFront.setReference(leftTargetPositionFront, ControlType.kPosition);
    leftPIDBack.setReference(leftTargetPositionBack, ControlType.kPosition);
    rightPIDFront.setReference(rightTargetPositionFront, ControlType.kPosition);
    rightPIDBack.setReference(rightTargetPositionBack, ControlType.kPosition);

  }

  /**
   * Checks to see if the robot is at the it's target position set from driveDistance()
   * @param threshold the maximum distance any wheel can be from it's target position, in meters
   * @return Whether or not the robot is at the target position
   */
  public boolean atTargetPosition(double threshold) {
    double lf = Math.abs(leftTargetPositionFront - leftEncoderFront.getPosition());
    double lb = Math.abs(leftTargetPositionBack - leftEncoderBack.getPosition());
    double rf = Math.abs(rightTargetPositionFront - rightEncoderBack.getPosition());
    double rb = Math.abs(rightTargetPositionBack - rightEncoderBack.getPosition());

    return (lf <= threshold && lb <= threshold && rf <= threshold && rb <= threshold);
  }

  public void setMaxSpeed(double max) {
    leftPIDFront.setOutputRange(-max, max);
    leftPIDBack.setOutputRange(-max, max);
    rightPIDFront.setOutputRange(-max, max);
    rightPIDBack.setOutputRange(-max, max);
  }

  public void stopDrive() {
    tankDriveVolts(0, 0);
  }

  public void feedDrive() {
    robotDrive.feed();
  }

}

