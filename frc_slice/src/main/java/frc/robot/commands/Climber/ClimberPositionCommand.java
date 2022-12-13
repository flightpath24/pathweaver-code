// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.Constants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A climber command that uses an climber subsystem. */
public class ClimberPositionCommand extends CommandBase {
  // Warning Supression
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  // Instance Variables
  private final Climber m_climber;
  
  double climberSpeed = 0;
  double targetLeftPosition;
  double targetRightPosition;

  boolean leftFinished = false;
  boolean rightFinished = false;

  double leftSpeed, rightSpeed;
  
  // Constructor
  public ClimberPositionCommand(Climber climber, double targetLeftPosition, double targetRightPosition) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);

    m_climber = climber;
    this.targetLeftPosition = targetLeftPosition;
    this.targetRightPosition = targetRightPosition;
  }

  // Method 1
  @Override
  public void initialize() {
    //Sets climber motors
    climberSpeed = Constants.climber_SPEED;
    leftFinished = false;
    rightFinished = false;
    leftSpeed = 0;
    rightSpeed = 0;
  }

  // Method 2
  @Override
  public void execute() {  

    if(Math.abs(Math.abs(targetLeftPosition) - Math.abs(m_climber.leftClimbPosition)) < 1) {
      leftFinished = true;
      leftSpeed = 0;
    } else if(targetLeftPosition > m_climber.leftClimbPosition) {
      leftSpeed = climberSpeed;
    } else if(targetLeftPosition < m_climber.leftClimbPosition) {
      leftSpeed = -climberSpeed;
    }

    if(Math.abs(Math.abs(targetLeftPosition) - Math.abs(m_climber.leftClimbPosition)) < 30) {
      leftSpeed *= 0.5;
    }

    if(Math.abs(Math.abs(targetRightPosition) - Math.abs(m_climber.rightClimbPosition)) < 1) {
      rightFinished = true;
      rightSpeed = 0;
    } else if(targetRightPosition > m_climber.rightClimbPosition) {
      rightSpeed = climberSpeed;
    } else if(targetRightPosition < m_climber.rightClimbPosition) {
      rightSpeed = -climberSpeed;
    }

    if(Math.abs(Math.abs(targetRightPosition) - Math.abs(m_climber.rightClimbPosition)) < 30) {
      rightSpeed *= 0.5;
    }

    m_climber.setLeftClimber(leftSpeed);
    m_climber.setRightClimber(rightSpeed);

  }

  // Method 3
  @Override
  public void end(boolean interrupted) {
    m_climber.setClimber(0);
  }

  // Method 4
  @Override
  public boolean isFinished() {
    return (rightFinished && leftFinished);
  }
}