// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.auto.Trajectories;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TrajectoryFollowerSequence extends SequentialCommandGroup {
  /** Creates a new TrajectoryFollowerSequence. */
  public TrajectoryFollowerSequence(Drivetrain drive, Trajectory trajectory) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    RamseteCommand ramseteCommand = Trajectories.generateRamseteCommand(drive, trajectory);
    addCommands(ramseteCommand, new InstantCommand(drive::stopDrive ,drive));
  }

  public TrajectoryFollowerSequence(Drivetrain drive, Trajectory trajectory, Pose2d pose) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    RamseteCommand ramseteCommand = Trajectories.generateRamseteCommand(drive, trajectory);
    ResetOdometryCommand resetOdometryCommand = new ResetOdometryCommand(drive, pose);
    addCommands(resetOdometryCommand, ramseteCommand, new InstantCommand(drive::stopDrive ,drive));
  }
}
