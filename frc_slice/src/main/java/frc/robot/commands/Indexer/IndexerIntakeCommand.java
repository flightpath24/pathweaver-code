// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An Indexer Intake command that uses an indexer and intake subsystem. */
public class IndexerIntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Indexer m_indexer;
  private final Intake m_intake;
  private boolean isForward;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public IndexerIntakeCommand(Indexer indexer, Intake intake, boolean isForward) {
    m_indexer = indexer;
    m_intake = intake;
    this.isForward = isForward;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer, intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isForward){
      m_indexer.SetIndexer(-0.5);
      m_intake.runIntake(true, false);
    } else if(!isForward){
      m_indexer.SetIndexer(0.5);
      m_intake.runIntake(false, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.SetIndexer(0);
    m_intake.runIntake(false, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
