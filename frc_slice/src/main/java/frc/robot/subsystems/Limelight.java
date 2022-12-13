// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */

  private final NetworkTable table;
  
  private double targetDetected;

  private double targetXOffset;

  private double targetYOffset;

  private final NetworkTableEntry lm;
  public Number ledMode;

  private final NetworkTableEntry cm;

  private final NetworkTableEntry pl;

  public Limelight() {

    table = NetworkTableInstance.getDefault().getTable("limelight-slice");

    lm = table.getEntry("ledMode");
    cm = table.getEntry("cameraMode");
    pl = table.getEntry("pipeline");

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    targetDetected = table.getEntry("tv").getDouble(0);
    targetXOffset = table.getEntry("tx").getDouble(0);
    targetYOffset = table.getEntry("ty").getDouble(0);

  }

  public double getTargetDetected() {

    return targetDetected;

  }

  public double getXOffset() {

    return targetXOffset;

  }

  public double getYOffset() {

    return targetYOffset;

  }

  //(NOTE TO SELF) If these methods below do not work, consider using forceSetDouble() instead.

  public void setLedMode(Number ledMode) {

    lm.setNumber(ledMode);

  }
  
  public void setCameraMode(int mode) {

    cm.setNumber(mode);

  }

  public void setPipeline() {

    pl.setNumber(0);

  }

}
