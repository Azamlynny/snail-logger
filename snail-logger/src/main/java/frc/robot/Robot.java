/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.*;
import java.io.*;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Robot extends TimedRobot {

  WPI_TalonSRX FrontRight;
  WPI_TalonSRX FrontLeft;
  WPI_TalonSRX BackRight;
  WPI_TalonSRX BackLeft;
  SpeedControllerGroup Right;
  SpeedControllerGroup Left;
  DifferentialDrive DriveTrain;
  XboxController Controller;
  PrintWriter out;
  AHRS navx;
  Timer timer;
  boolean printing;
  double driveSpeed;
  double turnSpeed;
  boolean enabled = false;

  @Override
  public void robotInit() {
    FrontLeft = new WPI_TalonSRX(2);
        BackLeft = new WPI_TalonSRX(3);
        BackRight = new WPI_TalonSRX(4);
        FrontRight = new WPI_TalonSRX(5);

        Right = new SpeedControllerGroup(FrontRight, BackRight);
        Left = new SpeedControllerGroup(FrontLeft, BackLeft);

        DriveTrain = new DifferentialDrive(Left, Right);

        Controller = new XboxController(0);

        driveSpeed = 0;
        turnSpeed = 0;
    printing = true;
    navx = new AHRS(SPI.Port.kMXP);
    timer = new Timer();
    try{
      out = new PrintWriter(new BufferedWriter(new FileWriter("/home/lvuser/log.txt")));
    }
    catch(IOException e){
      e.printStackTrace();
    } 
   
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit(){
    timer.start();
    timer.reset();
    enabled = true;
  }

  @Override
  public void teleopPeriodic() {
    
    driveSpeed = 0;
    turnSpeed = 0;

    double yaw = navx.getYaw();
    double pitch = navx.getPitch();
    double roll = navx.getRoll();
    double time = timer.get();
    SmartDashboard.putNumber("Yaw", yaw);
    SmartDashboard.putNumber("Pitch", pitch);
    SmartDashboard.putNumber("Roll", roll);
    SmartDashboard.putNumber("Timer", time);

    if(timer.get() < 1){
      out.println(Math.round(time) + " " + Math.round(yaw) + " " + Math.round(roll) + " " + Math.round(pitch));
      printing = false;
      timer.reset();
    }

    if(Controller.getAButton()) {
      double y = Controller.getY(GenericHID.Hand.kLeft);
      double x = Controller.getX(GenericHID.Hand.kLeft);
      driveSpeed = -y;
      turnSpeed = x;
      } 
      else if(Controller.getBumper(GenericHID.Hand.kLeft)) {
          double y = Controller.getY(GenericHID.Hand.kLeft);
          double x = Controller.getX(GenericHID.Hand.kRight);
          driveSpeed = -y;
          turnSpeed = x;
      } 
      else if(Controller.getBumper(GenericHID.Hand.kRight)) {
          double x = Controller.getX(GenericHID.Hand.kLeft);
          double y = Controller.getY(GenericHID.Hand.kRight);
          driveSpeed = -y;
          turnSpeed = x;
      }
      DriveTrain.arcadeDrive(driveSpeed, turnSpeed);

      if(Controller.getBButton()){
        out.close();
      }
  }

  @Override
  public void disabledInit(){
    // if(enabled){  
    //   out.close();
    //   enabled = false;
    // }

  }

  @Override
  public void testPeriodic() {
  }
}
