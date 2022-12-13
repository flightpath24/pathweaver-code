// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.*;

public class Indexer extends SubsystemBase {
  
  //Creates shooter motor objects
  private final CANSparkMax indexerMotor;
  //private final SparkMaxPIDController indexerPidController;
  //private final RelativeEncoder indexerEncoder;

  /** Creates a new Shooter. */
  public Indexer() {
    
    //Instantiates motors and encoders
    indexerMotor = new CANSparkMax(Constants.indexer_MOTOR_PORT, MotorType.kBrushless);
    //indexerPidController = indexerMotor.getPIDController();
    //indexerEncoder = indexerMotor.getEncoder();

    //indexerEncoder.setVelocityConversionFactor(2);

    indexerMotor.restoreFactoryDefaults();
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void SetIndexer(double Speed) { 

    //set motor speed with PWM
    indexerMotor.set(Speed);

    //set motor speed with PID (experimental)
    //indexerPidController.setReference(Speed, CANSparkMax.ControlType.kVelocity);
    
  }
}
