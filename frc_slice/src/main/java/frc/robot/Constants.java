// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //Define Joystick Ports
        public static final int RobotContainer_LEFT_JOYSTICK_PORT = 0;
        public static final int RobotContainer_RIGHT_JOYSTICK_PORT = 1;

    //Define Drivetrain Constants
        //Define Motor Ports
        public static final int drivetrain_LEFT_FRONT_PORT = 3;
        public static final int drivetrain_LEFT_BACK_PORT = 1;
        public static final int drivetrain_RIGHT_FRONT_PORT = 4;
        public static final int drivetrain_RIGHT_BACK_PORT = 2;

        //Define Encoder Ports
        public static final int[] drivetrain_LEFT_ENCODER = new int[] {0,1};
        public static final int[] drivetrain_RIGHT_ENCODER = new int[] {2,3};

        //Define Encoder Reveresed Booleans
        public static final boolean drivetrain_LEFT_ENCODER_REVERSED = false;
        public static final boolean drivetrain_RIGHT_ENCODER_REVERSED = false;

        //Define Encoder Wheel Information
        public static final int drivetrain_ENCODER_CPR = 42;
        public static final double drivetrain_WHEEL_DIAMETER_METERS = 0.15;

        public static final double drivetrain_VELOCITY_CONVERSION_FACTOR = Math.PI * drivetrain_WHEEL_DIAMETER_METERS /(60 * 10.75);
        public static final double drivetrain_POSITION_CONVERSION_RATIO = Math.PI * drivetrain_WHEEL_DIAMETER_METERS / 10.75;

        public static final double drivetrain_MAXIMUM_VELOCITY = 4.1644;

        //Define Encoder Distance Calculation Math
        public static final double drivetrain_ENCODER_DISTANCE_PER_PULSE =
            // Assumes the encoders are directly mounted on the wheel shafts
            drivetrain_VELOCITY_CONVERSION_FACTOR / (double) drivetrain_ENCODER_CPR;

    //Define Shooter Constants
        public static final int shooter_FLYWHEEL_PRIMARY_PORT = 9;
        public static final int shooter_FLYWHEEL_SECONDARY_PORT = 12;

        // Error threshold for shooters during smart shoot routine
        public static final double auto_shooter_threshold = 40;

    //Define Intake Motor Port
        public static final int intake_MOTOR_PORT = 11;

    //Define Indexer Motor Port
        public static final int indexer_MOTOR_PORT = 10;

    //Define Climber Constants
        public static final int climber_LEFT_MOTOR_PORT = 5;
        public static final int climber_RIGHT_MOTOR_PORT = 7;
        public static final double climber_SPEED = 0.5;

    //Define Limelight Constants
        public static final float limelight_STEERING_ADJUST_PROPORTION = 0.03f;
        public static final float limelight_MOVEMENT_ADJUST_PROPORTION = 0.04f;

        public static final double limelight_X_ALIGN_KP = 1;
        public static final double limelight_X_ALIGN_KI = 0.005;



    // ===============================
    // Autonomous
    // ===============================
        public static final double ksVolts = 0.19712;
        public static final double kvVoltsSecondsPerMeter = 2.7996;
        public static final double kaVoltsSecondsSquaredPerMeter = 0.38673;

        public static final double kPDriveVel = 3.6372;

        public static final double kTrackWidthMeters = 0.5842;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackWidthMeters);

        public static final double kMaxSpeedMetersPerSeconds = 0.1;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0.1;

        // Ramsete controller parameters. There are maethematically determined to be the best parameters for almost every robot
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

    // ===============================
    // Shooter Math
    // ===============================
        public static final double goalRadius = 0.61; //Radius of hub in meters
        public static final double hubHeight = 2.64; //Height of top goal off the ground in meters
        public static final double limelightHeight = 0.71755; //Height of the limelight off the ground in meters, NOT ACCURATE
        public static final double limelightAngle = 27.0; //Angle of limelight from horizontal, NOT ACCURATE

        public static final double secondaryPointDistance = -2.0; //Distance of secondary point from center of top goal
        public static final double secondaryPointHeight = 1.0; //Height of secondary point from center of top goal

        public static final double minimumShotDistance = 2.165; // The closest distance we've collected regression data from
        public static final double maximumShotDistance = 4.383; // The furthest distance we've collected regression data from
}
