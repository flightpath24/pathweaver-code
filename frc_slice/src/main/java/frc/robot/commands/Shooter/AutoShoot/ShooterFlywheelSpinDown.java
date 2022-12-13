// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter.AutoShoot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer; 

/** A Shooter command that uses a shooter subsystem. */
public class ShooterFlywheelSpinDown extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;

  double primarySpeed = 0;
  double secondarySpeed = 0;

  private Timer Time;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public ShooterFlywheelSpinDown(Shooter shooter) {
    m_shooter = shooter;

    Time = new Timer();
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Time.reset();
    Time.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Disables Shooter flywheels

    primarySpeed = 0;
    secondarySpeed = 0;

    m_shooter.SetShooters(primarySpeed, secondarySpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.SetShooters(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Time.get() >= .3) {
      return true;
    } 
    return false;
  }
}
