// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.Limelight.LimelightXCommand;
import frc.robot.commands.Indexer.IndexerIntakeCommand;
import frc.robot.commands.Indexer.AutoShoot.*;
import frc.robot.commands.Shooter.AutoShoot.*;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class smart2BallShootSequence extends SequentialCommandGroup {
  /** Creates a new alignedShootSequence. */
  public smart2BallShootSequence(Indexer indexer, Intake intake, Shooter shooter, Drivetrain drivetrain, Limelight limelight, 
      Joystick leftJoystick, Joystick rightJoystick) {

    LimelightXCommand horizontalAlign = new LimelightXCommand(limelight, drivetrain);
    ConstantSpeedShooterCommand spinUp = new ConstantSpeedShooterCommand(shooter, 0.5);
    ParallelDeadlineGroup horizontalAlignGroup = new ParallelDeadlineGroup(horizontalAlign, spinUp);

    addCommands(
      new TimedIndexerCommand(indexer, 0.3, 0.5),
      horizontalAlignGroup,
      new SmartAutoShoot(limelight, shooter),
      new TimedIndexerCommand(indexer, -0.5, 1.5),
      new WaitCommand(0.5),
      new SmartAutoShoot(limelight, shooter),
      new IndexerIntakeCommand(indexer, intake, true).withTimeout(1.5),
      new ConstantSpeedShooterCommand(shooter, 0)
    );
  }
}
