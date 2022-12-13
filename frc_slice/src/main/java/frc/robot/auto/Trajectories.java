// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import java.util.List;

//import java.util.List;

import edu.wpi.first.math.controller.*;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.trajectory.*;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

/** Add your docs here. */
public class Trajectories extends SequentialCommandGroup{
    
    // Create a voltage constraint to ensure we don't accelerate too fast
    public static DifferentialDriveVoltageConstraint autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                Constants.ksVolts,
                Constants.kvVoltsSecondsPerMeter,
                Constants.kaVoltsSecondsSquaredPerMeter),
            Constants.kDriveKinematics,
            10);

    // Create config for trajectory
    public static TrajectoryConfig config =
        new TrajectoryConfig(
                Constants.kMaxSpeedMetersPerSeconds,
                Constants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    /*//An example trajectory to follow.  All units in meters.
    public static Trajectory exampleTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 2), new Translation2d(2, -2)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config);*/

    // A test trajectory to follow.  All units in 3 meters.
    public static Trajectory testTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(0.3, 0.6), new Translation2d(0.6, -0.3)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(1, 0, new Rotation2d(0)),
            // Pass config
            config);
    /*public static Trajectory otherTrajectory = 
        TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(1, 0), new Translation2d(2,0)),
            new Pose2d(3, 0, new Rotation2d(0)),
            config);*/

    public static RamseteCommand generateRamseteCommand(Drivetrain drive, Trajectory trajectory) {
        RamseteCommand trajectoryFollower = new RamseteCommand(
            trajectory,
            drive::getEstimatedPosition,
            new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
            new SimpleMotorFeedforward(
                Constants.ksVolts,
                Constants.kvVoltsSecondsPerMeter,
                Constants.kaVoltsSecondsSquaredPerMeter),
            Constants.kDriveKinematics,
            drive::getWheelSpeeds,
            new PIDController(Constants.kPDriveVel, 0, 0),
            new PIDController(Constants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            drive::tankDriveVolts,
            drive);
        return trajectoryFollower;
    }










}
