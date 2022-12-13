// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import frc.robot.JoystickFilter;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.*;

/** A PIDDriveCommand command that uses a drivetrain subsystem. */
public class PIDDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_drivetrain;

  private final Joystick leftJoystick;
  private final Joystick rightJoystick;

  private final JoystickFilter forwardFilter, turnFilter;

  private final ShuffleboardTab driveTab;

  final SimpleWidget driveHeadingWidget;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PIDDriveCommand(Drivetrain drivetrain, Joystick leftJoystick, Joystick rightJoystick) {
    this.m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    this.leftJoystick = leftJoystick;
    this.rightJoystick = rightJoystick;

    forwardFilter = new JoystickFilter(0.1, 0.8);
    turnFilter = new JoystickFilter(0.1, 0.5);

    driveTab = Shuffleboard.getTab("Driver Tab");
    driveHeadingWidget = driveTab.add("Drive Heading", 0.0).withPosition(2, 2).withSize(2, 2).withWidget("Gyro");

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_drivetrain.setPIDF(.17, .000002, .12, .62);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //Sets robot speed and turn speed
    double forwardSpeed = forwardFilter.filter(leftJoystick.getY());
    double turnSpeed = turnFilter.filter(rightJoystick.getX());

    m_drivetrain.PIDArcadeDrive(forwardSpeed * 3.5, turnSpeed * 3);

    //Updates the odometry with a new estimated robot pose
    //m_drivetrain.updateOdometry();

    //Prints out the estimated robot pose
    //System.out.println(m_drivetrain.updateOdometry());

    //Prints out the rotation 2d heading
    SmartDashboard.putNumber("Drivetrain Heading:", m_drivetrain.getHeading());

    driveHeadingWidget.getEntry().setDouble(m_drivetrain.getHeading());

    //Prints out gyro turn rate
    SmartDashboard.putNumber("Drivetrain Turn Rate:", m_drivetrain.getTurnRate());    

    //Prints out left side velocity
    SmartDashboard.putNumber("Left Side Velocity:", m_drivetrain.getAverageLeftEncoderVelocity());

    //Prints out right side velocity
    SmartDashboard.putNumber("Right Side Velocity:", m_drivetrain.getAverageRightEncoderVelocity());

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
