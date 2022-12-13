// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import frc.robot.commands.Drivetrain.TrajectoryFollowerSequence;
//import frc.robot.commands.Indexer.AutoShoot.IndexerUpCommand;
//import frc.robot.commands.Intake.IntakeSchedulableCommand;
//import frc.robot.commands.Shooter.AutoShoot.SmartAutoShoot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

//import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class trajectoryFollowerAutoRoutine extends SequentialCommandGroup {

  /** Creates a new pathWeaverAutoRoutine. */
  public trajectoryFollowerAutoRoutine(Drivetrain drivetrain, Intake intake, Indexer indexer, Shooter shooter, Limelight limelight, Joystick leftJoystick, Joystick rightJoystick) {

    //Trajectories m_trajectories = new Trajectories();

    TrajectoryFollowerSequence path1TrajectoryForward = new TrajectoryFollowerSequence(drivetrain, Paths.getAutoPath().get(0), Paths.getAutoPath().get(0).getInitialPose());
    UpdateField2dCommand path1Field2d = new UpdateField2dCommand(1);
    ParallelDeadlineGroup path1ForwardGroup = new ParallelDeadlineGroup(path1TrajectoryForward, /*new IntakeSchedulableCommand(intake, true), path1Field2d*/ path1Field2d);

    TrajectoryFollowerSequence path2TrajectoryForward = new TrajectoryFollowerSequence(drivetrain, Paths.getAutoPath().get(1));
    UpdateField2dCommand path2Field2d = new UpdateField2dCommand(2);
    ParallelDeadlineGroup path2ForwardGroup = new ParallelDeadlineGroup(path2TrajectoryForward, /*new IntakeSchedulableCommand(intake, true),*/ path2Field2d);

    TrajectoryFollowerSequence path3TrajectoryForward = new TrajectoryFollowerSequence(drivetrain, Paths.getAutoPath().get(2));
    UpdateField2dCommand path3Field2d = new UpdateField2dCommand(3);
    ParallelDeadlineGroup path3ForwardGroup = new ParallelDeadlineGroup(path3TrajectoryForward, path3Field2d);


    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());S
    addCommands(
      //new IndexerUpCommand(indexer),
      path1ForwardGroup,
      //new smart2BallShootSequence(indexer, intake, shooter, drivetrain, limelight, leftJoystick, rightJoystick),
      path2ForwardGroup,
      //new IndexerUpCommand(indexer),
      path3ForwardGroup
      //new SmartAutoShoot(limelight, shooter)
    );

  }
  
}
