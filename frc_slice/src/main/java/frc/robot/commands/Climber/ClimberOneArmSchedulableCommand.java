// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.Constants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A climber command that uses an climber subsystem. */
public class ClimberOneArmSchedulableCommand extends CommandBase {
  // Warning Supression
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  // Instance Variables
  private final Climber m_climber;
  
  double climberSpeed = 0;
  boolean left;
  boolean isUp;
  
  // Constructor
  public ClimberOneArmSchedulableCommand(Climber climber, boolean isUp, boolean left /*true runs left, false runs right*/) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);

    m_climber = climber;
    this.left = left;
    this.isUp = isUp;
  }

  // Method 1
  @Override
  public void initialize() {
    
  }

  // Method 2
  @Override
  public void execute() {  
    //Sets climber motors
    climberSpeed = Constants.climber_SPEED;

    if(isUp){
      climberSpeed = -climberSpeed;
    }

    if(left) {
      m_climber.setLeftClimber(climberSpeed);
    } else {
      m_climber.setRightClimber(climberSpeed);
    }
  }

  // Method 3
  @Override
  public void end(boolean interrupted) {
    m_climber.setClimber(0);
  }

  // Method 4
  @Override
  public boolean isFinished() {
    return false;
  }
}