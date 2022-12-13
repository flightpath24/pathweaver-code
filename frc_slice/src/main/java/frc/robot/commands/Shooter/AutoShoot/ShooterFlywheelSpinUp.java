// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter.AutoShoot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A Shooter command that uses a shooter subsystem. */
public class ShooterFlywheelSpinUp extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;

  double primarySpeed = 0;
  double secondarySpeed = 0;

  private final Joystick leftJoystick;
  private final Joystick rightJoystick;

  private Timer Time;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public ShooterFlywheelSpinUp(Shooter shooter, Joystick leftJoystick, Joystick rightJoystick) {
    m_shooter = shooter;

    Time = new Timer();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);

    this.leftJoystick = leftJoystick;
    this.rightJoystick = rightJoystick;
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

    //Sets Shooter flywheels
    primarySpeed = rightJoystick.getZ();
    secondarySpeed = leftJoystick.getZ();

    //PWM only (too low for PID)
    //primarySpeed = -((primarySpeed + 1) / 2);
    //secondarySpeed = (secondarySpeed + 1) / 2;

    //PID ONLY (way too high for PWM)
    primarySpeed = -((primarySpeed + 1) * 2850);
    secondarySpeed = (secondarySpeed + 1) * 2850;

    m_shooter.SetShooters(1600, 400);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Time.get() >= 3) {
      return true;
    } 
    return false;
  }
}
