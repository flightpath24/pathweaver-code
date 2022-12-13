

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.*;


public class Pneumatics extends SubsystemBase
{
  // Instance Variables
  Joystick m_rightJoystick; 
  private final DoubleSolenoid dSolenoid;
  private final Compressor compressor;
  private boolean compressorEnabled;
  private boolean pressureSwitchValue;
  private double compressorCurrent;

  
  // Constructor
  public Pneumatics() {

    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    dSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    compressorEnabled = compressor.enabled();
    pressureSwitchValue = compressor.getPressureSwitchValue();
    compressorCurrent = compressor.getCurrent();

  }

  public void setSolenoid(DoubleSolenoid.Value pneumaticsMode) {

    dSolenoid.set(pneumaticsMode);

  }

  @Override
  public void periodic() 
  {
    SmartDashboard.putBoolean("Compressor status:", compressorEnabled);
    SmartDashboard.putBoolean("Pressure Switch Value:", pressureSwitchValue);
    SmartDashboard.putNumber("Compresor Current:", compressorCurrent);

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

