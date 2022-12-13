// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter.AutoShoot;

//import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class SmartAutoShoot extends CommandBase {
  // Subsystems
  private final Limelight m_limelight;
  private final Shooter m_shooter;

  //private final ShuffleboardTab regressionTab;
  //private final SimpleWidget measuredAngleOutput, measuredDistanceOutput, shotAngleOutput; 

  private double distanceToHub;
  private double angleToHub;

  /** Creates a new SmartAutoShooter. */
  public SmartAutoShoot(Limelight limelight, Shooter shooter) {
    m_limelight = limelight;
    m_shooter = shooter;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_limelight, m_shooter);

    // regressionTab = Shuffleboard.getTab("Regression");
    // measuredAngleOutput = regressionTab.add("Measured Angle to Goal but Again", 0.0);
    // measuredDistanceOutput = regressionTab.add("Measured Distance to Goal", 0.0);
    // shotAngleOutput = regressionTab.add("Desired Shot Angle", 0.0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    angleToHub = m_limelight.getYOffset();
    distanceToHub = m_shooter.getDistance(angleToHub);
    double shotAngle = m_shooter.getShotAngle(distanceToHub);
    double primarySpeed = m_shooter.getShotPower(distanceToHub);
    double secondarySpeed = m_shooter.getSecondaryMotorSpeed(primarySpeed, shotAngle);
    m_shooter.SetShooters(-primarySpeed, secondarySpeed);

    m_limelight.setCameraMode(0);
    m_limelight.setLedMode(3);

    //measuredAngleOutput.getEntry().setDouble(angleToHub);
    //measuredDistanceOutput.getEntry().setDouble(distanceToHub);
    //shotAngleOutput.getEntry().setDouble(shotAngle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.atTargetSpeed();
  }
}
