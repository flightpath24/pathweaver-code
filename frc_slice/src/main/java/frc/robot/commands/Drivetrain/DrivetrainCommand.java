// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import frc.robot.JoystickFilter;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Joystick;

//import edu.wpi.first.wpilibj.smartdashboard.*;

/** An example command that uses an example subsystem. */
public class DrivetrainCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_drivetrain;

  private final Joystick leftJoystick;
  private final Joystick rightJoystick;

  private final JoystickFilter forwardFilter, turnFilter;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DrivetrainCommand(Drivetrain drivetrain, Joystick leftJoystick, Joystick rightJoystick) {
    this.m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    this.leftJoystick = leftJoystick;
    this.rightJoystick = rightJoystick;

    forwardFilter = new JoystickFilter(0.05, 0.3);
    turnFilter = new JoystickFilter(0.05, 0.3);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //Resets gyro heading, encoder positions, and pose reading
    m_drivetrain.resetOdometry(new Pose2d(0.0, 0.0, new Rotation2d()));

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //Sets robot speed and turn speed
    double forwardSpeed = forwardFilter.filter(leftJoystick.getY());
    double turnSpeed = turnFilter.filter(rightJoystick.getX());

    m_drivetrain.ArcadeDrive(forwardSpeed, turnSpeed);

    //Updates the odometry with a new estimated robot pose
    m_drivetrain.updateOdometry();

    //Prints out the estimated robot pose
    System.out.println(m_drivetrain.updateOdometry());

    //Prints out the rotation 2d heading
    SmartDashboard.putNumber("Drivetrain Heading:", m_drivetrain.getHeading());

    //Prints out gyro turn rate
    SmartDashboard.putNumber("Drivetrain Turn Rate:", m_drivetrain.getTurnRate());

    //Prints out left side velocity
    SmartDashboard.putNumber("Left Side Velocity:", m_drivetrain.getAverageLeftEncoderVelocity());

    //Prints out right side velocity
    SmartDashboard.putNumber("Right Side Velocity:", m_drivetrain.getAverageRightEncoderVelocity());
    
    SmartDashboard.putNumber("Left Side Position: ", m_drivetrain.getAverageLeftEncoderDistance());
    SmartDashboard.putNumber("Right Side Position: ", m_drivetrain.getAverageRightEncoderDistance());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drivetrain.ArcadeDrive(0, 0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;
    
  }
}
