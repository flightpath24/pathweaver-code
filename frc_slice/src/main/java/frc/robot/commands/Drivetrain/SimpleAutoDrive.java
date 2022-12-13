// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class SimpleAutoDrive extends CommandBase {
  /** Creates a new SimpleAutoDrive. */
  private final Drivetrain m_drivetrain;
  private final double m_distance;

  public SimpleAutoDrive(Drivetrain drivetrain, Double distance) {
    m_drivetrain = drivetrain;
    m_distance = distance;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.setPIDF(0.8, 0.01, 0, 0);
    m_drivetrain.setMaxSpeed(0.3);
    m_drivetrain.driveDistance(m_distance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.ArcadeDrive(-0.7, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.stopDrive();
    m_drivetrain.setMaxSpeed(10);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_drivetrain.atTargetPosition(0.1);
  }
}
