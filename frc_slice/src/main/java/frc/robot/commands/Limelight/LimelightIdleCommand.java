// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class LimelightIdleCommand extends CommandBase {
  /** Creates a new LimelightRun. */

  private final Limelight m_limelight;

  public LimelightIdleCommand(Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);

    this.m_limelight = limelight;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelight.setCameraMode(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    m_limelight.setLedMode(1);
    m_limelight.setCameraMode(0);
    m_limelight.setPipeline();
    
    // Tell the driver if the robot sees the target and if the distance to the target is between tested values.
    if (m_limelight.getTargetDetected() == 1) {
      boolean goodShotDistance = Shooter.goodShotDistance(m_limelight.getYOffset());
      SmartDashboard.putBoolean("Good Shot Distance", goodShotDistance);
      SmartDashboard.putBoolean("Sees Target", true);
    } else {
      SmartDashboard.putBoolean("Good Shot Distance", false);
      SmartDashboard.putBoolean("Sees Target", false);
    }

    SmartDashboard.putNumber("Limelight X Offset", m_limelight.getXOffset());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_limelight.setLedMode(1);
    m_limelight.setCameraMode(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;

  }
}
