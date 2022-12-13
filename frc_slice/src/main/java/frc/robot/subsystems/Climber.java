// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.*;

//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;

public class Climber extends SubsystemBase {
  // Instance Variables
  private final CANSparkMax leftClimberMotor, rightClimberMotor;
  //private final MotorControllerGroup climberMotors;
  public final RelativeEncoder leftClimberEncoder, rightClimberEncoder;

  private double leftClimbPositionOffset = 0;
  private double rightClimbPositionOffset = 0;

  private double leftRawClimbPosition;
  private double rightRawClimbPosition;

  public double leftClimbPosition;
  public double rightClimbPosition;

  // Constructor
  public Climber() {
    // Assign individual motors
    rightClimberMotor = new CANSparkMax(Constants.climber_LEFT_MOTOR_PORT, MotorType.kBrushless);
    leftClimberMotor = new CANSparkMax(Constants.climber_RIGHT_MOTOR_PORT, MotorType.kBrushless);

    // Reset motors
    rightClimberMotor.restoreFactoryDefaults();
    leftClimberMotor.restoreFactoryDefaults();

    // Assign motor controller group
    //climberMotors = new MotorControllerGroup(rightClimberMotor, leftClimberMotor);

    // Assign Encoders
    leftClimberEncoder = leftClimberMotor.getEncoder();
    rightClimberEncoder = rightClimberMotor.getEncoder();

  }


  @Override
  public void periodic() {
    // Gets distance travelled
    leftRawClimbPosition = leftClimberEncoder.getPosition();
    rightRawClimbPosition = rightClimberEncoder.getPosition();

    leftClimbPosition = leftRawClimbPosition - leftClimbPositionOffset;
    rightClimbPosition = rightRawClimbPosition - rightClimbPositionOffset;

    // Gets encoder current rate
    leftClimberEncoder.getVelocity();
    rightClimberEncoder.getVelocity();

    SmartDashboard.putNumber("Left Climber Position", leftClimbPosition);
    SmartDashboard.putNumber("Right Climber Position", rightClimbPosition);

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void setClimber(double climberSpeed) {
    leftClimberMotor.set(climberSpeed);  
    rightClimberMotor.set(climberSpeed);
  }

  public void setLeftClimber(double climberSpeed) {
    leftClimberMotor.set(climberSpeed);  
  }

  public void setRightClimber(double climberSpeed) {
    rightClimberMotor.set(climberSpeed);
  }

  public void zeroClimberPosition() {
    leftClimbPositionOffset = leftRawClimbPosition;
    rightClimbPositionOffset = rightRawClimbPosition;
  }
}