// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class LimelightScheduleableCommand extends CommandBase {
  /** Creates a new LimelightRun. */

  private final Limelight m_limelight;

  private final Drivetrain m_drivetrain;

  static double targetDetected;
  static double targetXOffset;
  static double targetYOffset;

  double xSteeringAdjust;
  double ySteeringAdjust;

  boolean finished;

  public LimelightScheduleableCommand(Limelight limelight, Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelight);

    this.m_limelight = limelight;
    this.m_drivetrain = drivetrain;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelight.setCameraMode(1);

    finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    targetDetected = m_limelight.getTargetDetected();
    targetXOffset = m_limelight.getXOffset();
    targetYOffset = m_limelight.getYOffset();

    if(targetDetected == 1) {

      if (targetXOffset > 10) {
        xSteeringAdjust = Constants.limelight_STEERING_ADJUST_PROPORTION * 10;
      } else if (targetXOffset < -10) {
        xSteeringAdjust = Constants.limelight_STEERING_ADJUST_PROPORTION * -10;
      } else {
        xSteeringAdjust = Constants.limelight_STEERING_ADJUST_PROPORTION * targetXOffset;
      }

      /*if (1 < targetYOffset && targetYOffset < 6) {
        ySteeringAdjust = Constants.limelight_MOVEMENT_ADJUST_PROPORTION * -6;
      } else if (-1 > targetYOffset && targetYOffset > -6) {
        ySteeringAdjust = Constants.limelight_MOVEMENT_ADJUST_PROPORTION * 6;
      } else {
        ySteeringAdjust = Constants.limelight_MOVEMENT_ADJUST_PROPORTION * targetYOffset;
      }*/

      ySteeringAdjust = Constants.limelight_MOVEMENT_ADJUST_PROPORTION * targetYOffset;
      
      m_drivetrain.ArcadeDrive(ySteeringAdjust, xSteeringAdjust);

    } else {
      ySteeringAdjust = 0;
      xSteeringAdjust = 15 * Constants.limelight_STEERING_ADJUST_PROPORTION;

      m_drivetrain.ArcadeDrive(ySteeringAdjust, xSteeringAdjust);
    }

    if (targetDetected == 1 && java.lang.Math.abs(targetXOffset) < 2 && java.lang.Math.abs(targetYOffset) < 4) {
      finished = true;
    }

    SmartDashboard.putNumber("Limelight Target Detected:", targetDetected);
    SmartDashboard.putNumber("Limelight X Offset", targetXOffset);
    SmartDashboard.putNumber("Limelight Y Offset", targetYOffset);

    m_limelight.setLedMode(3);
    m_limelight.setCameraMode(0);
    m_limelight.setPipeline();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_limelight.setLedMode(1);
    m_limelight.setCameraMode(1);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return finished;

  }
}
