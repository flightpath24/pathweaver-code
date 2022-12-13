// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class UpdateField2dCommand extends CommandBase {
  /** Creates a new field2dCommand. */
  int m_trajectoryNumber;

  public UpdateField2dCommand(int trajectoryNumber) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_trajectoryNumber = trajectoryNumber;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Drivetrain.updateField2d(m_trajectoryNumber);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
