// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.drive.SwerveDrive;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.WoS;
import frc.robot.testingdashboard.TDSendable;
import frc.robot.testingdashboard.TestingDashboard;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Handle to Operator Inputs
  private OI m_oi;
  private final WoS m_WoS = new WoS();

  // The robot's subsystems are defined here.
  private final Drive m_robotDrive;
  // private final SendableChooser<Command> m_autoChooser;
  private PowerDistribution m_pdBoard;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // load configuration
    RobotMap.init();

    m_pdBoard = new PowerDistribution(1, ModuleType.kRev);
    m_pdBoard.setSwitchableChannel(true);

    m_oi = OI.getInstance();

    // Instantiate parameterized commands to register them with the testing dashboard.
    // The first instance of a Command registers itself. No need to store the resulting
    // objects.
    registerCommands();

    // Robot subsystems initialized and configured here
    m_robotDrive = Drive.getInstance();
    m_robotDrive.setDefaultCommand(new SwerveDrive(m_oi.getDriveInputs()));

    // Build the auto commands and add them to the chooser
    // m_autoChooser = AutoBuilder.buildAutoChooser("closeAutoTop_startMid");
    // new TDSendable(Drive.getInstance(), "Auto Commands", "Chooser", m_autoChooser);
    
    // Configure the trigger/button bindings
    configureBindings();

    // Create Testing Dashboard
    TestingDashboard.getInstance().createTestingDashboard();
    // SmartDashboard.putData(m_autoChooser);
  }

  private void registerCommands() {

    //TDNumber testX = new TDNumber(Drive.getInstance(), "Test Inputs", "TargetPoseX");
    //TDNumber testY = new TDNumber(Drive.getInstance(), "Test Inputs", "TargetPoseY");
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    OI.getInstance().bindControls();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null; //m_autoChooser.getSelected();
  }
}