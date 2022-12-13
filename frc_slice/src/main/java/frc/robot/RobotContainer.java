// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.auto.*;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.Indexer.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.Limelight.*;
import frc.robot.commands.Pneumatics.*;
import frc.robot.commands.Climber.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  static final Drivetrain m_drivetrain = new Drivetrain();
  static final Shooter m_shooter = new Shooter();
  static final Indexer m_indexer = new Indexer();
  static final Intake m_intake = new Intake();
  static final Limelight m_limelight = new Limelight();
  static final Climber m_climber = new Climber();
  static final Pneumatics m_pneumatics = new Pneumatics();

  private static Joystick leftJoystick = Button.leftJoystick;
  private static Joystick rightJoystick = Button.rightJoystick;

  static final alignlessShootSequence m_alignlessShootAuto = 
        new alignlessShootSequence(m_indexer, m_shooter, leftJoystick, rightJoystick);

  static final smartShootSequence m_smartShootAuto = 
        new smartShootSequence(m_indexer, m_shooter, m_drivetrain, m_limelight, leftJoystick, rightJoystick);
      
  //static final RamseteCommand m_testTrajectory = Trajectories.generateRamseteCommand(m_drivetrain, Trajectories.testTrajectory);

  static final LimelightScheduleableCommand m_limelightAlign = new LimelightScheduleableCommand(m_limelight, m_drivetrain);

  static final IndexerSchedulableCommand m_runIndexerUp = new IndexerSchedulableCommand(m_indexer, -0.5);
  static final IndexerSchedulableCommand m_runIndexerDown = new IndexerSchedulableCommand(m_indexer, 0.5);
 
  static final IntakeSchedulableCommand m_forwardIntake = new IntakeSchedulableCommand(m_intake, true);
  static final IntakeSchedulableCommand m_reverseIntake = new IntakeSchedulableCommand(m_intake, false);

  static final IndexerIntakeCommand m_inIntakeIndexer = new IndexerIntakeCommand(m_indexer, m_intake, true);
  static final IndexerIntakeCommand m_outIntakeIndexer = new IndexerIntakeCommand(m_indexer, m_intake, false);

  static final ClimberSchedulableCommand m_extendClimbers = new ClimberSchedulableCommand(m_climber, true);
  static final ClimberSchedulableCommand m_retractClimbers = new ClimberSchedulableCommand(m_climber, false);
  static final ClimberOneArmSchedulableCommand m_extendLeftClimbers = new ClimberOneArmSchedulableCommand(m_climber, true, true);
  static final ClimberOneArmSchedulableCommand m_retractLeftClimbers = new ClimberOneArmSchedulableCommand(m_climber, false, true);
  static final ClimberOneArmSchedulableCommand m_extendRightClimbers = new ClimberOneArmSchedulableCommand(m_climber, true, false);
  static final ClimberOneArmSchedulableCommand m_retractRightClimbers = new ClimberOneArmSchedulableCommand(m_climber, false, false);

  static final climbPositionInSequence m_minClimbers = new climbPositionInSequence(m_climber, m_pneumatics, 0, 0);
  static final climbPositionInSequence m_verticalClimbers = new climbPositionInSequence(m_climber, m_pneumatics, -328, -291);
  static final climbPositionOutSequence m_backClimbers = new climbPositionOutSequence(m_climber, m_pneumatics, -340, -320);
  static final ClimberZeroPositionCommand m_zeroClimbers = new ClimberZeroPositionCommand(m_climber);

  static final PneumaticsInCommand m_inPneumatics = new PneumaticsInCommand(m_pneumatics);
  static final PneumaticsOutCommand m_outPneumatics = new PneumaticsOutCommand(m_pneumatics);

  static final DrivetrainCommand m_oldDrivetrain = new DrivetrainCommand(m_drivetrain, leftJoystick, rightJoystick); 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    Paths.createAutoPaths();

    // Configure the button bindings
    configureButtonBindings();

    m_drivetrain.setDefaultCommand(new PIDDriveCommand(m_drivetrain, leftJoystick, rightJoystick));
    m_indexer.setDefaultCommand(new IndexerSchedulableCommand(m_indexer, 0));
    m_shooter.setDefaultCommand(new ShooterIdleCommand(m_shooter));
    m_intake.setDefaultCommand(new IntakeIdleCommand(m_intake));
    m_limelight.setDefaultCommand(new LimelightIdleCommand(m_limelight));
    m_climber.setDefaultCommand(new ClimberIdleCommand(m_climber));
    m_pneumatics.setDefaultCommand(new PneumaticsIdleCommand(m_pneumatics));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Shoot without aligning
    Button.rightTrigger.whenHeld(m_alignlessShootAuto);

    //Any* distance shooting
    Button.leftTrigger.whenHeld(m_smartShootAuto);

    //Run indexer up
    Button.indexerUpFast.whenHeld(m_runIndexerUp);
    
    //Run indexer down
    Button.indexerDownFast.whenHeld(m_runIndexerDown);

    //Toggle intake
    Button.intakeToggle.toggleWhenPressed(m_forwardIntake);

    //Reverse intake
    Button.intakeReverse.whenHeld(m_reverseIntake);

    //Run indexer and intake simultaneously
    Button.indexerIntakeIn.whenHeld(m_inIntakeIndexer);
    Button.indexerIntakeOut.whenHeld(m_outIntakeIndexer);

    //Run both climbers
    Button.climberArmsUp.whenHeld(m_extendClimbers);
    Button.climberArmsDown.whenHeld(m_retractClimbers);

    //Run left climbers
    Button.leftClimberUp.whenHeld(m_extendLeftClimbers);
    Button.leftClimberDown.whenHeld(m_retractLeftClimbers);

    //Run right climbers
    Button.rightClimberUp.whenHeld(m_extendRightClimbers);
    Button.rightClimberDown.whenHeld(m_retractRightClimbers);

    //Toggle Pneumatics
    Button.pneumaticsIn.whenHeld(m_inPneumatics);
    Button.pneumaticsOut.whenHeld(m_outPneumatics);

    //Set position of climbers and pneumatics
    Button.climberPneumaticsMin.whenHeld(m_minClimbers);
    Button.climberPneumaticsUp.whenHeld(m_verticalClimbers);
    Button.climberPneumaticsBack.whenHeld(m_backClimbers);
    Button.zeroClimber.whenPressed(m_zeroClimbers);

    Button.driveMethod.toggleWhenPressed(m_oldDrivetrain);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return new TrajectoryFollowerSequence(m_drivetrain, Trajectories.testTrajectory, Trajectories.testTrajectory.getInitialPose());
    //return new simpleAutoRoutine(m_indexer, m_intake, m_shooter, m_drivetrain, m_limelight, leftJoystick, rightJoystick);
    return new trajectoryFollowerAutoRoutine(m_drivetrain, m_intake, m_indexer, m_shooter, m_limelight, leftJoystick, rightJoystick);
  }
}