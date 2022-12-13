// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer.AutoShoot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer; 

/** An Indexer command that uses an indexer subsystem. */
public class IndexerDownSlightCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Indexer m_indexer;

  double Speed = 0;
  private Timer Time;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public IndexerDownSlightCommand(Indexer indexer) {
    m_indexer = indexer;

    Time = new Timer();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer);
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
    
    Speed = 0.2;
    
    m_indexer.SetIndexer(Speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.SetIndexer(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if(Time.get() >= 0.3) {
      return true;
    } else {
      return false;
    }

  }
}
