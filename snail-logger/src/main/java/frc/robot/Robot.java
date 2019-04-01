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

public class Robot extends TimedRobot {

  PrintWriter out;
  AHRS navx;
  Timer timer;
  boolean printing;
  
  @Override
  public void robotInit() {

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
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Yaw", navx.getYaw());
    SmartDashboard.putNumber("Pitch", navx.getPitch());
    SmartDashboard.putNumber("Roll", navx.getRoll());
    SmartDashboard.putNumber("Timer", timer.get());

    out.println(timer.get() + " " + navx.getYaw() + " " + navx.getRoll() + " " + navx.getPitch() + " " + "True");
    if(timer.get() > 10 && printing){
      out.close();
      printing = false;
    }
  }

  @Override
  public void disabledInit(){
      out.close();
  }

  @Override
  public void testPeriodic() {
  }
}
