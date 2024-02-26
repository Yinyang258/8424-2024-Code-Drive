// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs
 * the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  // private static XboxController controller = new XboxController(0);
  private static GenericHID leftJoystick = new GenericHID(1);
  private static GenericHID rightJoystick = new GenericHID(2);
  private static GenericHID operator = new GenericHID(3);

  private final CANSparkMax leftFront = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax leftBack = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax rightFront = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax rightBack = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax lifter = new CANSparkMax(8, MotorType.kBrushless);
  private final CANSparkMax intake = new CANSparkMax(7, MotorType.kBrushless);
  private final CANSparkMax leftShooter = new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax rightShooter = new CANSparkMax(5, MotorType.kBrushless);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftFront::set, rightFront::set);


  public Robot() {
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    rightFront.setInverted(true);
    rightBack.setInverted(true);
    leftBack.follow(leftFront);
    rightBack.follow(rightFront);

    rightShooter.setInverted(true);

    CameraServer.startAutomaticCapture();//.setResolution(1280, 720);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(leftJoystick.getRawAxis(1) * -.8, rightJoystick.getRawAxis(0) * -.8);

    lifter.set(operator.getRawAxis(1) * 0.6);

    if (operator.getRawButton(3)) {
      intake.set(0.8);
    } else if (operator.getRawButton(4)) {
      intake.set(-.25);
    } else {
      intake.set(0);
    }

    if (operator.getRawButton(2)) {
      rightShooter.set(-0.20);
      leftShooter.set(-0.20);
    } else if (operator.getRawButton(1)) {
      rightShooter.set(-0.9);
      leftShooter.set(-0.9);
    } else {
      rightShooter.set(0);
      leftShooter.set(0);
    }
  }
}
