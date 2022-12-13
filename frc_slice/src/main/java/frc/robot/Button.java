// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public final class Button {

    //Define Joysticks
    public static Joystick leftJoystick = new Joystick(Constants.RobotContainer_LEFT_JOYSTICK_PORT);
    public static Joystick rightJoystick = new Joystick(Constants.RobotContainer_RIGHT_JOYSTICK_PORT);

    //Define Drivetrain Buttons
    public static JoystickButton driveMethod = new JoystickButton(leftJoystick, 10); //Left Bottom 10

    //Define Indexer Buttons
    public static JoystickButton indexerUpFast = new JoystickButton(rightJoystick, 5); //Right Top 5
    public static JoystickButton indexerDownFast = new JoystickButton(leftJoystick, 5); //Left Top 5

    //Define Intake + Indexer Buttons
    public static JoystickButton indexerIntakeIn = new JoystickButton(rightJoystick, 3); //Right Top 3
    public static JoystickButton indexerIntakeOut = new JoystickButton(leftJoystick, 3); //Left Top 3

    //Define Intake Buttons
    public static JoystickButton intakeToggle = new JoystickButton(rightJoystick, 2); //Right Top 2
    public static JoystickButton intakeReverse = new JoystickButton(leftJoystick, 2); //Left Top 2

    //Define Shooter Buttons
    public static JoystickButton rightTrigger = new JoystickButton(rightJoystick, 1); //Right Top 1
    public static JoystickButton leftTrigger = new JoystickButton(leftJoystick, 1); //Left Top 1

    //Define Climber Buttons
    public static JoystickButton climberArmsDown = new JoystickButton(rightJoystick, 7); //Right Bottom 7
    public static JoystickButton climberArmsUp = new JoystickButton(rightJoystick, 8); //Right Bottom 8
    public static JoystickButton leftClimberDown = new JoystickButton(rightJoystick, 9); //Right Bottom 9
    public static JoystickButton leftClimberUp = new JoystickButton(rightJoystick, 10); //Right Bottom 10
    public static JoystickButton rightClimberDown = new JoystickButton(rightJoystick, 11); //Right Bottom 9
    public static JoystickButton rightClimberUp = new JoystickButton(rightJoystick, 12); //Right Bottom 10
    public static JoystickButton zeroClimber = new JoystickButton(leftJoystick, 9); //Left Bottom 9

    //Define Pneumatics Buttons
    public static JoystickButton pneumaticsOut = new JoystickButton(leftJoystick, 7); //Left Bottom 7
    public static JoystickButton pneumaticsIn = new JoystickButton(leftJoystick, 8); //Left Bottom 8

    //Define Climber + Pneumatics Buttons
    public static JoystickButton climberPneumaticsBack = new JoystickButton(leftJoystick, 4); //Left Top 4
    public static JoystickButton climberPneumaticsMin = new JoystickButton(rightJoystick, 4); //Right Top 4
    public static JoystickButton climberPneumaticsUp = new JoystickButton(rightJoystick, 6); //Left Top 6

    //Unassigned Right Joystick Buttons

    //Unassigned Left Joystick Buttons
    public static JoystickButton leftButton6 = new JoystickButton(leftJoystick, 6); //Left Top 6
    public static JoystickButton leftButton11 = new JoystickButton(leftJoystick, 11); //Left Bottom 11
    public static JoystickButton leftButton12 = new JoystickButton(leftJoystick, 12); //Left Bottom 12
    

}
