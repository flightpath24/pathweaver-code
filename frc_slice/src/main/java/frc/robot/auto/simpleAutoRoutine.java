// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.Indexer.AutoShoot.IndexerDownSlightCommand;
//import frc.robot.commands.Indexer.AutoShoot.TimedIndexerCommand;
import frc.robot.commands.Intake.IntakeSchedulableCommand;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class simpleAutoRoutine extends SequentialCommandGroup {

  /** Creates a new simpleAutoRoutine. */
  public simpleAutoRoutine(Indexer indexer, Intake intake, Shooter shooter, Drivetrain drivetrain, Limelight limelight, 
  Joystick leftJoystick, Joystick rightJoystick) {

    SimpleAutoDrive forward = new SimpleAutoDrive(drivetrain, 2.0);
    IntakeSchedulableCommand runIntake = new IntakeSchedulableCommand(intake, true);
    ParallelDeadlineGroup forwardGroup = new ParallelDeadlineGroup(forward, runIntake);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ResetOdometryCommand(drivetrain, new Pose2d()),
      /*new TimedIndexerCommand(indexer, -0.5, 0.5),*/
      new IndexerDownSlightCommand(indexer),
      forwardGroup,
      new smart2BallShootSequence(indexer, intake, shooter, drivetrain, limelight, leftJoystick, rightJoystick)
    );
  }
}
